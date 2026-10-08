package com.placement.controller;

import com.placement.model.Student;
import com.placement.model.User;
import com.placement.util.DatabaseConnection;
import com.placement.dao.StudentDAO;
import com.placement.dao.StudentDAOImpl;

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

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        int totalApplications = 0;
        int shortlisted = 0;
        int pendingInterviews = 0;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            Student student = studentDAO.getStudentByUserId(user.getUserId());
            
            // Auto-heal missing student profile
            if (student == null) {
                com.placement.dao.UserDAO userDAO = new com.placement.dao.UserDAOImpl();
                userDAO.createStudentProfile(user.getUserId(), "Student", "User", "N/A");
                student = studentDAO.getStudentByUserId(user.getUserId());
            }
            
            int studentId = student.getStudentId();
            
            // Total Applications
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM applications WHERE student_id = ?")) {
                stmt.setInt(1, studentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) totalApplications = rs.getInt(1);
                }
            }
            
            // Shortlisted
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM applications WHERE student_id = ? AND status = 'SHORTLISTED'")) {
                stmt.setInt(1, studentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) shortlisted = rs.getInt(1);
                }
            }
            
            // Pending Interviews
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM applications WHERE student_id = ? AND status = 'INTERVIEW_SCHEDULED'")) {
                stmt.setInt(1, studentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) pendingInterviews = rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("totalApplications", totalApplications);
        request.setAttribute("shortlisted", shortlisted);
        request.setAttribute("pendingInterviews", pendingInterviews);
        
        request.getRequestDispatcher("/jsp/student/dashboard.jsp").forward(request, response);
    }
}