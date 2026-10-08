package com.placement.controller;

import com.placement.model.Company;
import com.placement.model.User;
import com.placement.util.DatabaseConnection;
import com.placement.dao.CompanyDAO;
import com.placement.dao.CompanyDAOImpl;

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

@WebServlet("/recruiter/dashboard")
public class RecruiterDashboardServlet extends HttpServlet {
    private CompanyDAO companyDAO;

    public void init() {
        companyDAO = new CompanyDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        int activePostings = 0;
        int totalApplicants = 0;
        int selectedCandidates = 0;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            Company company = companyDAO.getCompanyByUserId(user.getUserId());
            
            if (company == null) {
                // If the recruiter hasn't set up their company profile yet, redirect them
                request.setAttribute("error", "Please complete your company profile before accessing the dashboard.");
                request.getRequestDispatcher("/jsp/recruiter/profile.jsp").forward(request, response);
                return;
            }
            
            int companyId = company.getCompanyId();
            
            // Active Postings
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM opportunities WHERE company_id = ? AND status = 'OPEN'")) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) activePostings = rs.getInt(1);
                }
            }
            
            // Total Applicants
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM applications a JOIN opportunities o ON a.opp_id = o.opp_id WHERE o.company_id = ?")) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) totalApplicants = rs.getInt(1);
                }
            }
            
            // Selected Candidates
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM applications a JOIN opportunities o ON a.opp_id = o.opp_id WHERE o.company_id = ? AND a.status = 'SELECTED'")) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) selectedCandidates = rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("activePostings", activePostings);
        request.setAttribute("totalApplicants", totalApplicants);
        request.setAttribute("selectedCandidates", selectedCandidates);
        
        request.getRequestDispatcher("/jsp/recruiter/dashboard.jsp").forward(request, response);
    }
}
