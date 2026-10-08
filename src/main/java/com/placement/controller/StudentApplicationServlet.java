package com.placement.controller;

import com.placement.dao.ApplicationDAO;
import com.placement.dao.ApplicationDAOImpl;
import com.placement.dao.StudentDAO;
import com.placement.dao.StudentDAOImpl;
import com.placement.dao.OpportunityDAO;
import com.placement.dao.OpportunityDAOImpl;
import com.placement.model.Application;
import com.placement.model.Student;
import com.placement.model.User;
import com.placement.model.Opportunity;
import com.placement.service.EligibilityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/student/apply", "/student/applications"})
public class StudentApplicationServlet extends HttpServlet {
    private ApplicationDAO appDAO;
    private StudentDAO studentDAO;
    private OpportunityDAO oppDAO;

    public void init() {
        appDAO = new ApplicationDAOImpl();
        studentDAO = new StudentDAOImpl();
        oppDAO = new OpportunityDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/student/applications".equals(path)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
            try {
                Student student = studentDAO.getStudentByUserId(user.getUserId());
                
                // Auto-heal missing student profile
                if (student == null) {
                    com.placement.dao.UserDAO userDAO = new com.placement.dao.UserDAOImpl();
                    userDAO.createStudentProfile(user.getUserId(), "Student", "User", "N/A");
                    student = studentDAO.getStudentByUserId(user.getUserId());
                }
                
                List<Application> apps = appDAO.getApplicationsByStudent(student.getStudentId());
                
                request.setAttribute("applications", apps);
                request.getRequestDispatcher("/jsp/student/applications.jsp").forward(request, response);
                
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        if ("/student/apply".equals(path)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            int oppId = Integer.parseInt(request.getParameter("oppId"));
            
            try {
                Student student = studentDAO.getStudentByUserId(user.getUserId());
                
                // Auto-heal missing student profile
                if (student == null) {
                    com.placement.dao.UserDAO userDAO = new com.placement.dao.UserDAOImpl();
                    userDAO.createStudentProfile(user.getUserId(), "Student", "User", "N/A");
                    student = studentDAO.getStudentByUserId(user.getUserId());
                }
                
                Opportunity opp = oppDAO.getOpportunityById(oppId);
                
                // Server side verification of eligibility
                EligibilityService.EligibilityResult result = EligibilityService.checkEligibility(student, opp);
                if (!result.isEligible()) {
                    response.sendRedirect(request.getContextPath() + "/student/opportunities?error=not_eligible");
                    return;
                }
                
                boolean success = appDAO.apply(student.getStudentId(), oppId);
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/student/applications?success=applied");
                } else {
                    response.sendRedirect(request.getContextPath() + "/student/applications?error=already_applied");
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error applying to job");
            }
        }
    }
}
