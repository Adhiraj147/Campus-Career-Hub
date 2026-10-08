package com.placement.controller;

import com.placement.dao.CompanyDAO;
import com.placement.dao.CompanyDAOImpl;
import com.placement.dao.OpportunityDAO;
import com.placement.dao.OpportunityDAOImpl;
import com.placement.model.Company;
import com.placement.model.Opportunity;
import com.placement.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/recruiter/opportunities")
public class OpportunityServlet extends HttpServlet {
    private OpportunityDAO oppDAO;
    private CompanyDAO companyDAO;

    public void init() {
        oppDAO = new OpportunityDAOImpl();
        companyDAO = new CompanyDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        try {
            Company company = companyDAO.getCompanyByUserId(user.getUserId());
            List<Opportunity> opps = oppDAO.getOpportunitiesByCompany(company.getCompanyId());
            
            request.setAttribute("opportunities", opps);
            request.getRequestDispatcher("/jsp/recruiter/postings.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");
        
        try {
            Company company = companyDAO.getCompanyByUserId(user.getUserId());
            
            if ("add".equals(action)) {
                Opportunity opp = new Opportunity();
                opp.setCompanyId(company.getCompanyId());
                opp.setTitle(request.getParameter("title"));
                opp.setType(request.getParameter("type"));
                opp.setDescription(request.getParameter("description"));
                opp.setLocation(request.getParameter("location"));
                opp.setWorkMode(request.getParameter("workMode"));
                opp.setSalaryStipend(request.getParameter("salaryStipend"));
                opp.setMinCgpa(Double.parseDouble(request.getParameter("minCgpa")));
                opp.setEligibleBranches(request.getParameter("eligibleBranches"));
                opp.setGraduationYearReq(Integer.parseInt(request.getParameter("graduationYearReq")));
                opp.setRequiredSkills(request.getParameter("requiredSkills"));
                opp.setDeadline(Date.valueOf(request.getParameter("deadline"))); // format: yyyy-mm-dd
                
                oppDAO.addOpportunity(opp);
                
            } else if ("close".equals(action)) {
                int oppId = Integer.parseInt(request.getParameter("oppId"));
                oppDAO.closeOpportunity(oppId);
            }
            
            response.sendRedirect(request.getContextPath() + "/recruiter/opportunities");
            
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error managing opportunity");
        }
    }
}
