package com.placement.controller;

import com.placement.dao.ApplicationDAO;
import com.placement.dao.ApplicationDAOImpl;
import com.placement.dao.CompanyDAO;
import com.placement.dao.CompanyDAOImpl;
import com.placement.dao.OpportunityDAO;
import com.placement.dao.OpportunityDAOImpl;
import com.placement.model.Application;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/recruiter/applicants", "/recruiter/update-status"})
public class RecruiterApplicationServlet extends HttpServlet {
    private ApplicationDAO appDAO;
    private CompanyDAO companyDAO;
    private OpportunityDAO oppDAO;

    public void init() {
        appDAO = new ApplicationDAOImpl();
        companyDAO = new CompanyDAOImpl();
        oppDAO = new OpportunityDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        if ("/recruiter/applicants".equals(path)) {
            String oppIdStr = request.getParameter("oppId");
            if (oppIdStr == null) {
                response.sendRedirect(request.getContextPath() + "/recruiter/opportunities");
                return;
            }
            
            try {
                int oppId = Integer.parseInt(oppIdStr);
                Opportunity opp = oppDAO.getOpportunityById(oppId);
                List<Application> applicants = appDAO.getApplicationsByOpportunity(oppId);
                
                request.setAttribute("opportunity", opp);
                request.setAttribute("applicants", applicants);
                request.getRequestDispatcher("/jsp/recruiter/applicants.jsp").forward(request, response);
                
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        if ("/recruiter/update-status".equals(path)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
            int appId = Integer.parseInt(request.getParameter("appId"));
            int oppId = Integer.parseInt(request.getParameter("oppId"));
            String status = request.getParameter("status");
            String remarks = request.getParameter("remarks");
            
            try {
                appDAO.updateStatus(appId, status, remarks, user.getUserId());
                response.sendRedirect(request.getContextPath() + "/recruiter/applicants?oppId=" + oppId + "&success=1");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating status");
            }
        }
    }
}
