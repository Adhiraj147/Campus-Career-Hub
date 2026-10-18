package com.placement.controller;

import com.placement.dao.ApplicationDAO;
import com.placement.dao.ApplicationDAOImpl;
import com.placement.dao.CompanyDAO;
import com.placement.dao.CompanyDAOImpl;
import com.placement.dao.NotificationDAO;
import com.placement.dao.NotificationDAOImpl;
import com.placement.dao.OpportunityDAO;
import com.placement.dao.OpportunityDAOImpl;
import com.placement.model.Application;
import com.placement.model.Company;
import com.placement.model.Notification;
import com.placement.model.Opportunity;
import com.placement.model.User;
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

@WebServlet(urlPatterns = {"/recruiter/applicants", "/recruiter/update-status"})
public class RecruiterApplicationServlet extends HttpServlet {
    private ApplicationDAO appDAO;
    private CompanyDAO companyDAO;
    private OpportunityDAO oppDAO;
    private NotificationDAO notificationDAO;

    public void init() {
        appDAO = new ApplicationDAOImpl();
        companyDAO = new CompanyDAOImpl();
        oppDAO = new OpportunityDAOImpl();
        notificationDAO = new NotificationDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        if ("/recruiter/applicants".equals(path)) {
            String oppIdStr = request.getParameter("oppId");
            if (oppIdStr == null) {
                response.sendRedirect(request.getContextPath() + "/recruiter/opportunities");
                return;
            }
            
            try {
                int oppId = Integer.parseInt(oppIdStr);
                Opportunity opp = oppDAO.getOpportunityById(oppId);
                List<Application> applicants = appDAO.getApplicationsByOpportunity(oppId);
                
                request.setAttribute("opportunity", opp);
                request.setAttribute("applicants", applicants);
                request.getRequestDispatcher("/jsp/recruiter/applicants.jsp").forward(request, response);
                
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        if ("/recruiter/update-status".equals(path)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
            int appId = Integer.parseInt(request.getParameter("appId"));
            int oppId = Integer.parseInt(request.getParameter("oppId"));
            String status = request.getParameter("status");
            String remarks = request.getParameter("remarks");
            
            try {
                appDAO.updateStatus(appId, status, remarks, user.getUserId());

                // ── Fire in-app notification to the student ─────────────────
                try {
                    int studentUserId = getStudentUserIdByApplication(appId);
                    String oppTitle = getOppTitle(oppId);
                    if (studentUserId > 0) {
                        Notification n = new Notification();
                        n.setUserId(studentUserId);
                        n.setNotificationType("APP_STATUS");
                        n.setRelatedAppId(appId);
                        n.setRelatedOppId(oppId);
                        String msg = "Your application for \"" + oppTitle + "\" has been updated to: " + formatStatus(status) + ".";
                        if (remarks != null && !remarks.trim().isEmpty()) {
                            msg += " Remarks: " + remarks.trim();
                        }
                        n.setMessage(msg);
                        notificationDAO.create(n);
                    }
                } catch (Exception notifEx) {
                    // Notification failure must never block the status update
                    notifEx.printStackTrace();
                }

                response.sendRedirect(request.getContextPath() + "/recruiter/applicants?oppId=" + oppId + "&success=1");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating status");
            }
        }
    }

    /** Look up the user_id of the student who owns this application. */
    private int getStudentUserIdByApplication(int appId) throws SQLException {
        String sql = "SELECT s.user_id FROM applications a " +
                     "JOIN students s ON a.student_id = s.student_id " +
                     "WHERE a.application_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("user_id") : -1;
            }
        }
    }

    private String getOppTitle(int oppId) {
        try {
            Opportunity opp = oppDAO.getOpportunityById(oppId);
            return opp != null ? opp.getTitle() : "the position";
        } catch (SQLException e) {
            return "the position";
        }
    }

    private String formatStatus(String status) {
        if (status == null) return "";
        switch (status) {
            case "APPLIED":              return "Applied";
            case "SHORTLISTED":          return "Shortlisted";
            case "INTERVIEW_SCHEDULED":  return "Interview Scheduled";
            case "OFFERED":              return "Offered";
            case "REJECTED":             return "Rejected";
            default:                     return status;
        }
    }
}
