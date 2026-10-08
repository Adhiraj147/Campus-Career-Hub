package com.placement.controller;

import com.placement.dao.StudentDAO;
import com.placement.dao.StudentDAOImpl;
import com.placement.model.Education;
import com.placement.model.Project;
import com.placement.model.Student;
import com.placement.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/student/profile")
public class StudentProfileServlet extends HttpServlet {
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        try {
            Student student = studentDAO.getStudentByUserId(user.getUserId());
            
            // Auto-heal missing student profile
            if (student == null) {
                com.placement.dao.UserDAO userDAO = new com.placement.dao.UserDAOImpl();
                userDAO.createStudentProfile(user.getUserId(), "Student", "User", "N/A");
                student = studentDAO.getStudentByUserId(user.getUserId());
            }
            
            request.setAttribute("student", student);
            request.setAttribute("completionScore", student.calculateProfileCompletion());
            request.getRequestDispatcher("/jsp/student/profile.jsp").forward(request, response);
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
            Student student = studentDAO.getStudentByUserId(user.getUserId());
            
            // Auto-heal missing student profile
            if (student == null) {
                com.placement.dao.UserDAO userDAO = new com.placement.dao.UserDAOImpl();
                userDAO.createStudentProfile(user.getUserId(), "Student", "User", "N/A");
                student = studentDAO.getStudentByUserId(user.getUserId());
            }
            
            int studentId = student.getStudentId();
            
            if ("updateBasic".equals(action)) {
                student.setFirstName(request.getParameter("firstName"));
                student.setLastName(request.getParameter("lastName"));
                student.setRollNo(request.getParameter("rollNo"));
                student.setBranch(request.getParameter("branch"));
                student.setGraduationYear(Integer.parseInt(request.getParameter("graduationYear")));
                student.setCgpa(Double.parseDouble(request.getParameter("cgpa")));
                student.setPhone(request.getParameter("phone"));
                student.setResumeUrl(request.getParameter("resumeUrl"));
                
                studentDAO.updateStudentBasicProfile(student);
                
            } else if ("addEducation".equals(action)) {
                Education edu = new Education();
                edu.setStudentId(studentId);
                edu.setDegree(request.getParameter("degree"));
                edu.setInstitution(request.getParameter("institution"));
                edu.setPassingYear(Integer.parseInt(request.getParameter("passingYear")));
                edu.setPercentage(Double.parseDouble(request.getParameter("percentage")));
                
                studentDAO.addEducation(edu);
                
            } else if ("deleteEducation".equals(action)) {
                int eduId = Integer.parseInt(request.getParameter("eduId"));
                studentDAO.deleteEducation(eduId);
                
            } else if ("addProject".equals(action)) {
                Project proj = new Project();
                proj.setStudentId(studentId);
                proj.setTitle(request.getParameter("title"));
                proj.setDescription(request.getParameter("description"));
                proj.setLink(request.getParameter("link"));
                
                studentDAO.addProject(proj);
                
            } else if ("deleteProject".equals(action)) {
                int projId = Integer.parseInt(request.getParameter("projectId"));
                studentDAO.deleteProject(projId);
                
            } else if ("addSkill".equals(action)) {
                String skillName = request.getParameter("skillName");
                if (skillName != null && !skillName.trim().isEmpty()) {
                    studentDAO.addSkill(studentId, skillName.trim());
                }
            } else if ("removeSkill".equals(action)) {
                String skillName = request.getParameter("skillName");
                studentDAO.removeSkill(studentId, skillName);
            }
            
            response.sendRedirect(request.getContextPath() + "/student/profile");
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating profile");
        }
    }
}
