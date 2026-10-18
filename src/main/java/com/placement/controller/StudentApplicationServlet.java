package com.placement.controller;

import com.placement.dao.ApplicationDAO;
import com.placement.dao.ApplicationDAOImpl;
import com.placement.dao.NotificationDAO;
import com.placement.dao.NotificationDAOImpl;
import com.placement.dao.OpportunityDAO;
import com.placement.dao.OpportunityDAOImpl;
import com.placement.dao.StudentDAO;
import com.placement.dao.StudentDAOImpl;
import com.placement.model.Application;
import com.placement.model.Notification;
import com.placement.model.Opportunity;
import com.placement.model.Student;
import com.placement.model.User;
import com.placement.service.EligibilityService;
import com.placement.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/student/apply", "/student/applications"})
public class StudentApplicationServlet extends HttpServlet {
    private ApplicationDAO appDAO;
    private StudentDAO studentDAO;
    private OpportunityDAO oppDAO;
    private NotificationDAO notificationDAO;

    public void init() {
        appDAO = new ApplicationDAOImpl();
        studentDAO = new StudentDAOImpl();
        oppDAO = new OpportunityDAOImpl();
        notificationDAO = new NotificationDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/student/applications".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            User user = (User) session.getAttribute("user");

            try {
                Student student = studentDAO.getStudentByUserId(user.getUserId());

                if (student == null) {
                    request.setAttribute("profileMissing", true);
                    request.setAttribute("applications", new java.util.ArrayList<>());
                    request.getRequestDispatcher("/jsp/student/applications.jsp").forward(request, response);
                    return;
                }

                List<Application> apps = appDAO.getApplicationsByStudent(student.getStudentId());
                request.setAttribute("applications", apps);
                request.getRequestDispatcher("/jsp/student/applications.jsp").forward(request, response);

            } catch (SQLException e) {
                System.err.println("[StudentApplicationServlet] DB error for userId=" + user.getUserId() + ": " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "A database error occurred while loading your applications. Please try again later.");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/student/apply".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            User user = (User) session.getAttribute("user");
            int oppId = Integer.parseInt(request.getParameter("oppId"));

            try {
                Student student = studentDAO.getStudentByUserId(user.getUserId());

                if (student == null) {
                    response.sendRedirect(request.getContextPath() + "/student/opportunities?error=no_profile");
                    return;
                }

                Opportunity opp = oppDAO.getOpportunityById(oppId);

                // Server-side eligibility check
                EligibilityService.EligibilityResult result = EligibilityService.checkEligibility(student, opp);
                if (!result.isEligible()) {
                    response.sendRedirect(request.getContextPath() + "/student/opportunities?error=not_eligible");
                    return;
                }

                boolean success = appDAO.apply(student.getStudentId(), oppId);
                if (success) {
                    // ── Fire notification to the recruiter ──────────────────
                    try {
                        int recruiterUserId = getRecruiterUserIdByOpp(oppId);
                        if (recruiterUserId > 0) {
                            String studentName = student.getFirstName() + " " + student.getLastName();
                            Notification n = new Notification();
                            n.setUserId(recruiterUserId);
                            n.setNotificationType("NEW_APPLICATION");
                            n.setRelatedOppId(oppId);
                            n.setMessage("New application received: " + studentName +
                                    " (Branch: " + student.getBranch() + ", CGPA: " + student.getCgpa() + ")" +
                                    " applied for \"" + opp.getTitle() + "\".");
                            notificationDAO.create(n);
                        }
                    } catch (Exception notifEx) {
                        // Notification failure must never block the application
                        notifEx.printStackTrace();
                    }
                    response.sendRedirect(request.getContextPath() + "/student/applications?success=applied");
                } else {
                    response.sendRedirect(request.getContextPath() + "/student/applications?error=already_applied");
                }

            } catch (SQLException e) {
                System.err.println("[StudentApplicationServlet] Error applying userId=" + user.getUserId() + " oppId=" + oppId + ": " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error submitting application. Please try again later.");
            }
        }
    }

    /**
     * Finds the user_id of the recruiter who owns the company that posted this opportunity.
     */
    private int getRecruiterUserIdByOpp(int oppId) throws SQLException {
        String sql = "SELECT u.user_id FROM opportunities o " +
                     "JOIN companies c ON o.company_id = c.company_id " +
                     "JOIN users u ON c.user_id = u.user_id " +
                     "WHERE o.opp_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, oppId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("user_id") : -1;
            }
        }
    }
}
