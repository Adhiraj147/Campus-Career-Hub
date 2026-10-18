package com.placement.controller;

import com.placement.dao.NotificationDAO;
import com.placement.dao.NotificationDAOImpl;
import com.placement.dao.StudentDAO;
import com.placement.dao.StudentDAOImpl;
import com.placement.model.RecommendationResult;
import com.placement.model.Student;
import com.placement.model.User;
import com.placement.service.RecommendationService;
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

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {
    private StudentDAO studentDAO;
    private NotificationDAO notificationDAO;
    private RecommendationService recommendationService;

    public void init() {
        studentDAO = new StudentDAOImpl();
        notificationDAO = new NotificationDAOImpl();
        recommendationService = new RecommendationService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");

        int totalApplications = 0;
        int shortlisted = 0;
        int pendingInterviews = 0;
        int unreadNotifications = 0;

        try {
            Student student = studentDAO.getStudentByUserId(user.getUserId());

            if (student != null) {
                int studentId = student.getStudentId();

                try (Connection conn = DatabaseConnection.getConnection()) {
                    // Total Applications
                    try (PreparedStatement stmt = conn.prepareStatement(
                            "SELECT COUNT(*) FROM applications WHERE student_id = ?")) {
                        stmt.setInt(1, studentId);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) totalApplications = rs.getInt(1);
                        }
                    }

                    // Shortlisted
                    try (PreparedStatement stmt = conn.prepareStatement(
                            "SELECT COUNT(*) FROM applications WHERE student_id = ? AND status = 'SHORTLISTED'")) {
                        stmt.setInt(1, studentId);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) shortlisted = rs.getInt(1);
                        }
                    }

                    // Pending Interviews
                    try (PreparedStatement stmt = conn.prepareStatement(
                            "SELECT COUNT(*) FROM applications WHERE student_id = ? AND status = 'INTERVIEW_SCHEDULED'")) {
                        stmt.setInt(1, studentId);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) pendingInterviews = rs.getInt(1);
                        }
                    }
                }

                // Top 3 recommendations preview for dashboard
                try {
                    List<RecommendationResult> recs = recommendationService.getRecommendations(student);
                    int limit = Math.min(3, recs.size());
                    request.setAttribute("topRecommendations", recs.subList(0, limit));
                } catch (Exception e) {
                    // Never let recommendation errors break the dashboard
                    e.printStackTrace();
                }
            }

            // Unread notification count
            try {
                unreadNotifications = notificationDAO.getUnreadCount(user.getUserId());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("[StudentDashboardServlet] Database error: " + e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("totalApplications", totalApplications);
        request.setAttribute("shortlisted", shortlisted);
        request.setAttribute("pendingInterviews", pendingInterviews);
        request.setAttribute("unreadNotifications", unreadNotifications);

        // Also push to session so header.jsp can display the bell badge on all pages
        request.getSession().setAttribute("unreadNotifications", unreadNotifications);

        request.getRequestDispatcher("/jsp/student/dashboard.jsp").forward(request, response);
    }
}