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
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validation
        if(email == null || password == null || role == null) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            // Check if user already exists
            if (userDAO.getUserByEmail(email) != null) {
                request.setAttribute("error", "Email is already registered.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }
            
            // Hash password
            String hashedPassword = PasswordUtil.hashPassword(password);
            
            // Default status
            String status = "ACTIVE"; 
            if ("RECRUITER".equals(role)) {
                status = "PENDING"; // Recruiters might need admin approval
            }
            
            User user = new User(email, hashedPassword, role, status);
            int newUserId = -1;
            
            try {
                if ("STUDENT".equals(role)) {
                    String firstName = request.getParameter("firstName");
                    String lastName = request.getParameter("lastName");
                    String rollNo = request.getParameter("rollNo");
                    
                    if (firstName == null || firstName.trim().isEmpty()) firstName = "Student";
                    if (lastName == null || lastName.trim().isEmpty()) lastName = "User";
                    if (rollNo == null || rollNo.trim().isEmpty()) rollNo = "PENDING-" + System.currentTimeMillis();
                    
                    newUserId = userDAO.registerStudentWithTransaction(user, firstName, lastName, rollNo);
                } else if ("RECRUITER".equals(role)) {
                    String companyName = request.getParameter("companyName");
                    if (companyName == null || companyName.trim().isEmpty()) companyName = "My Company";
                    newUserId = userDAO.registerRecruiterWithTransaction(user, companyName);
                }
            } catch (SQLException e) {
                String sqlState = e.getSQLState();
                if ("23000".equals(sqlState) && e.getMessage() != null && e.getMessage().contains("roll_no")) {
                    request.setAttribute("error", "Roll number is already registered.");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
                throw e; // Rethrow to let the main catch block handle it
            }
            
            if (newUserId > 0) {
                request.setAttribute("success", "Registration successful. Please login.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}