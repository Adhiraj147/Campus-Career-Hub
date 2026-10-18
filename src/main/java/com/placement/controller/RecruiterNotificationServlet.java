package com.placement.controller;

import com.placement.dao.NotificationDAO;
import com.placement.dao.NotificationDAOImpl;
import com.placement.model.Notification;
import com.placement.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {
    "/recruiter/notifications",
    "/recruiter/notifications/mark-read",
    "/recruiter/notifications/mark-all-read"
})
public class RecruiterNotificationServlet extends HttpServlet {

    private NotificationDAO notificationDAO;

    @Override
    public void init() {
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
        String path = request.getServletPath();

        if ("/recruiter/notifications/mark-read".equals(path)) {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                try {
                    notificationDAO.markRead(Integer.parseInt(idStr), user.getUserId());
                } catch (SQLException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect(request.getContextPath() + "/recruiter/notifications");
            return;
        }

        if ("/recruiter/notifications/mark-all-read".equals(path)) {
            try {
                notificationDAO.markAllRead(user.getUserId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/recruiter/notifications");
            return;
        }

        // Default — show list
        try {
            List<Notification> notifications = notificationDAO.getByUserId(user.getUserId());
            int unreadCount = notificationDAO.getUnreadCount(user.getUserId());
            request.setAttribute("notifications", notifications);
            request.setAttribute("unreadCount", unreadCount);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Could not load notifications.");
        }

        request.getRequestDispatcher("/jsp/recruiter/notifications.jsp").forward(request, response);
    }
}
