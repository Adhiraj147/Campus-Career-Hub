package com.placement.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/jsp/*")
public class AuthFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        
        String loginURI = req.getContextPath() + "/login";

        // If not logged in, redirect to login page
        if (!loggedIn) {
            res.sendRedirect(loginURI);
        } else {
            // Further role-based checking
            String path = req.getRequestURI();
            String role = (String) session.getAttribute("role");
            
            if (path.contains("/student/") && !"STUDENT".equals(role)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            } else if (path.contains("/recruiter/") && !"RECRUITER".equals(role)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            } else if (path.contains("/admin/") && !"ADMIN".equals(role)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
            
            chain.doFilter(request, response);
        }
    }

    public void destroy() {}
}
