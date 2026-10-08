package com.placement.controller;

import com.placement.dao.OpportunityDAO;
import com.placement.dao.OpportunityDAOImpl;
import com.placement.dao.StudentDAO;
import com.placement.dao.StudentDAOImpl;
import com.placement.model.Opportunity;
import com.placement.model.Student;
import com.placement.model.User;
import com.placement.service.EligibilityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/student/opportunities")
public class StudentOpportunityServlet extends HttpServlet {
    private OpportunityDAO oppDAO;
    private StudentDAO studentDAO;

    public void init() {
        oppDAO = new OpportunityDAOImpl();
        studentDAO = new StudentDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String keyword = request.getParameter("keyword");
        String type = request.getParameter("type");
        
        try {
            Student student = studentDAO.getStudentByUserId(user.getUserId());
            List<Opportunity> opps;
            
            if ((keyword != null && !keyword.isEmpty()) || (type != null && !type.isEmpty())) {
                opps = oppDAO.searchOpportunities(keyword, type);
            } else {
                opps = oppDAO.getAllOpenOpportunities();
            }
            
            // Calculate eligibility for each opportunity
            Map<Integer, EligibilityService.EligibilityResult> eligibilityMap = new HashMap<>();
            for (Opportunity opp : opps) {
                eligibilityMap.put(opp.getOppId(), EligibilityService.checkEligibility(student, opp));
            }
            
            request.setAttribute("opportunities", opps);
            request.setAttribute("eligibilityMap", eligibilityMap);
            request.setAttribute("keyword", keyword);
            request.setAttribute("type", type);
            
            request.getRequestDispatcher("/jsp/student/opportunities.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
