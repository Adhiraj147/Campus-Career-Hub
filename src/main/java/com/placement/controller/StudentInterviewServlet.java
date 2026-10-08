package com.placement.controller;

import com.placement.dao.ApplicationDAO;
import com.placement.dao.ApplicationDAOImpl;
import com.placement.dao.StudentDAO;
import com.placement.dao.StudentDAOImpl;
import com.placement.model.Application;
import com.placement.model.Student;
import com.placement.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/student/interviews")
public class StudentInterviewServlet extends HttpServlet {
    private ApplicationDAO appDAO;
    private StudentDAO studentDAO;

    public void init() {
        appDAO = new ApplicationDAOImpl();
        studentDAO = new StudentDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
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
            
            List<Application> allApps = appDAO.getApplicationsByStudent(student.getStudentId());
            
            // Filter to only show applications with INTERVIEW_SCHEDULED status
            List<Application> interviews = new ArrayList<>();
            for (Application app : allApps) {
                if ("INTERVIEW_SCHEDULED".equals(app.getStatus())) {
                    interviews.add(app);
                }
            }
            
            request.setAttribute("interviews", interviews);
            request.getRequestDispatcher("/jsp/student/interviews.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
