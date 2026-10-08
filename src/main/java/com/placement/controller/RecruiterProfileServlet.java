package com.placement.controller;

import com.placement.dao.CompanyDAO;
import com.placement.dao.CompanyDAOImpl;
import com.placement.model.Company;
import com.placement.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/recruiter/profile")
public class RecruiterProfileServlet extends HttpServlet {
    private CompanyDAO companyDAO;

    public void init() {
        companyDAO = new CompanyDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        try {
            Company company = companyDAO.getCompanyByUserId(user.getUserId());
            request.setAttribute("company", company);
            request.getRequestDispatcher("/jsp/recruiter/profile.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        try {
            Company company = companyDAO.getCompanyByUserId(user.getUserId());
            
            // If the company profile doesn't exist yet, we must create it first
            if (company == null) {
                com.placement.dao.UserDAO userDAO = new com.placement.dao.UserDAOImpl();
                String companyName = request.getParameter("companyName");
                if (companyName == null || companyName.trim().isEmpty()) {
                    companyName = "My Company";
                }
                userDAO.createCompanyProfile(user.getUserId(), companyName);
                
                // Now fetch the newly created company
                company = companyDAO.getCompanyByUserId(user.getUserId());
            }
            
            company.setCompanyName(request.getParameter("companyName"));
            company.setDescription(request.getParameter("description"));
            company.setWebsite(request.getParameter("website"));
            company.setIndustry(request.getParameter("industry"));
            company.setLocation(request.getParameter("location"));
            company.setContactPerson(request.getParameter("contactPerson"));
            
            companyDAO.updateCompanyProfile(company);
            response.sendRedirect(request.getContextPath() + "/recruiter/profile?success=1");
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating profile");
        }
    }
}
