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

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");

        String keyword = request.getParameter("keyword");
        String type = request.getParameter("type");

        try {
            // Look up the student — may be null if they have not completed registration.
            Student student = studentDAO.getStudentByUserId(user.getUserId());

            List<Opportunity> opps;
            if ((keyword != null && !keyword.isEmpty()) || (type != null && !type.isEmpty())) {
                opps = oppDAO.searchOpportunities(keyword, type);
            } else {
                opps = oppDAO.getAllOpenOpportunities();
            }

            // Calculate eligibility for each opportunity.
            // If the student profile is missing, mark every opportunity as ineligible
            // with a clear "please complete your profile" message — never pass null
            // to EligibilityService.
            Map<Integer, EligibilityService.EligibilityResult> eligibilityMap = new HashMap<>();
            for (Opportunity opp : opps) {
                if (student == null) {
                    // Profile does not exist yet — ineligible until profile is created.
                    eligibilityMap.put(opp.getOppId(),
                        new EligibilityService.EligibilityResult(false,
                            "Please complete your student profile before applying."));
                } else {
                    eligibilityMap.put(opp.getOppId(),
                        EligibilityService.checkEligibility(student, opp));
                }
            }

            request.setAttribute("opportunities", opps);
            request.setAttribute("eligibilityMap", eligibilityMap);
            request.setAttribute("keyword", keyword);
            request.setAttribute("type", type);
            // Tell the JSP if the profile is missing so it can show a banner.
            request.setAttribute("profileMissing", student == null);

            request.getRequestDispatcher("/jsp/student/opportunities.jsp").forward(request, response);

        } catch (SQLException e) {
            System.err.println("[StudentOpportunityServlet] Database error for userId=" + user.getUserId() + ": " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "A database error occurred while loading opportunities. Please try again later.");
        }
    }
}
