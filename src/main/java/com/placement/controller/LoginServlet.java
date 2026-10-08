package com.placement.controller;

import com.placement.dao.UserDAO;
import com.placement.dao.UserDAOImpl;
import com.placement.model.User;
import com.placement.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()){
            request.setAttribute("error", "Email and Password are required");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.getUserByEmail(email);
            
            if (user != null && PasswordUtil.checkPassword(password, user.getPasswordHash())) {
                if ("INACTIVE".equals(user.getStatus())) {
                    request.setAttribute("error", "Your account is inactive.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                }
                
                // Success: create session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());
                
                // Redirect based on role
                if ("STUDENT".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/student/dashboard");
                } else if ("RECRUITER".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/recruiter/dashboard");
                } else if ("ADMIN".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                }
            } else {
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
