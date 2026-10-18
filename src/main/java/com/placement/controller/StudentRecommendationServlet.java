package com.placement.controller;

import com.placement.dao.NotificationDAO;
import com.placement.dao.NotificationDAOImpl;
import com.placement.dao.StudentDAO;
import com.placement.dao.StudentDAOImpl;
import com.placement.model.Notification;
import com.placement.model.RecommendationResult;
import com.placement.model.Student;
import com.placement.model.User;
import com.placement.service.RecommendationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/student/recommendations")
public class StudentRecommendationServlet extends HttpServlet {

    private StudentDAO studentDAO;
    private RecommendationService recommendationService;
    private NotificationDAO notificationDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAOImpl();
        recommendationService = new RecommendationService();
        notificationDAO = new NotificationDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            Student student = studentDAO.getStudentByUserId(user.getUserId());
            if (student == null) {
                request.setAttribute("error", "Please complete your profile to receive recommendations.");
                request.getRequestDispatcher("/jsp/student/recommendations.jsp").forward(request, response);
                return;
            }

            List<RecommendationResult> recommendations = recommendationService.getRecommendations(student);

            // Fire NEW_MATCH notifications for top eligible recommendations (score >= 50)
            for (RecommendationResult rec : recommendations) {
                if (rec.getScore() >= 50 && rec.getEligibility().isEligible()) {
                    try {
                        boolean alreadyNotified = notificationDAO.hasNewMatchNotification(
                                user.getUserId(), rec.getOpportunity().getOppId());
                        if (!alreadyNotified) {
                            Notification n = new Notification();
                            n.setUserId(user.getUserId());
                            n.setNotificationType("NEW_MATCH");
                            n.setRelatedOppId(rec.getOpportunity().getOppId());
                            n.setMessage("New match: \"" + rec.getOpportunity().getTitle() +
                                    "\" at " + rec.getOpportunity().getCompanyName() +
                                    " — " + rec.getScore() + "% match score.");
                            notificationDAO.create(n);
                        }
                    } catch (SQLException e) {
                        // Notification failure must never break the recommendations page
                        e.printStackTrace();
                    }
                }
            }

            request.setAttribute("recommendations", recommendations);
            request.setAttribute("student", student);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Could not load recommendations. Please try again later.");
        }

        request.getRequestDispatcher("/jsp/student/recommendations.jsp").forward(request, response);
    }
}
