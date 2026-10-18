package com.placement.filter;

import com.placement.dao.NotificationDAO;
import com.placement.dao.NotificationDAOImpl;
import com.placement.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Refreshes the unread notification count in the session on every
 * student request so the navbar bell badge is always up-to-date —
 * not just after visiting the dashboard.
 */
@WebFilter("/student/*")
public class NotificationFilter implements Filter {

    private NotificationDAO notificationDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        notificationDAO = new NotificationDAOImpl();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpSession session = httpReq.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            try {
                int unread = notificationDAO.getUnreadCount(user.getUserId());
                session.setAttribute("unreadNotifications", unread);
            } catch (Exception e) {
                // Never block the request over a failed count query
                e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
