package com.placement.dao;

import com.placement.model.*;
import com.placement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public Student getStudentByUserId(int userId) throws SQLException {
        Student student = null;
        String sql = "SELECT * FROM students WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setUserId(rs.getInt("user_id"));
                    student.setFirstName(rs.getString("first_name"));
                    student.setLastName(rs.getString("last_name"));
                    student.setRollNo(rs.getString("roll_no"));
                    student.setBranch(rs.getString("branch"));
                    student.setGraduationYear(rs.getInt("graduation_year"));
                    student.setCgpa(rs.getDouble("cgpa"));
                    student.setPhone(rs.getString("phone"));
                    student.setResumeUrl(rs.getString("resume_url"));
                    student.setProfileCompleted(rs.getBoolean("profile_completed"));
                }
            }
        }
        
        if (student != null) {
            student.setEducationList(getEducationList(student.getStudentId()));
            student.setProjectList(getProjectList(student.getStudentId()));
            student.setSkills(getSkillsList(student.getStudentId()));
            // Certifications and Experiences can be populated similarly if requested
        }
        return student;
    }

    @Override
    public boolean updateStudentBasicProfile(Student student) throws SQLException {
        String sql = "UPDATE students SET first_name=?, last_name=?, roll_no=?, branch=?, graduation_year=?, cgpa=?, phone=?, resume_url=?, profile_completed=true WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getRollNo());
            stmt.setString(4, student.getBranch());
            stmt.setInt(5, student.getGraduationYear());
            stmt.setDouble(6, student.getCgpa());
            stmt.setString(7, student.getPhone());
            stmt.setString(8, student.getResumeUrl());
            stmt.setInt(9, student.getStudentId());
            return stmt.executeUpdate() > 0;
        }
    }

    private List<Education> getEducationList(int studentId) throws SQLException {
        List<Education> list = new ArrayList<>();
        String sql = "SELECT * FROM education WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Education edu = new Education();
                    edu.setEduId(rs.getInt("edu_id"));
                    edu.setStudentId(rs.getInt("student_id"));
                    edu.setDegree(rs.getString("degree"));
                    edu.setInstitution(rs.getString("institution"));
                    edu.setPassingYear(rs.getInt("passing_year"));
                    edu.setPercentage(rs.getDouble("percentage"));
                    list.add(edu);
                }
            }
        }
        return list;
    }

    @Override
    public boolean addEducation(Education edu) throws SQLException {
        String sql = "INSERT INTO education (student_id, degree, institution, passing_year, percentage) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, edu.getStudentId());
            stmt.setString(2, edu.getDegree());
            stmt.setString(3, edu.getInstitution());
            stmt.setInt(4, edu.getPassingYear());
            stmt.setDouble(5, edu.getPercentage());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteEducation(int eduId) throws SQLException {
        String sql = "DELETE FROM education WHERE edu_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eduId);
            return stmt.executeUpdate() > 0;
        }
    }

    private List<Project> getProjectList(int studentId) throws SQLException {
        List<Project> list = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Project proj = new Project();
                    proj.setProjectId(rs.getInt("project_id"));
                    proj.setStudentId(rs.getInt("student_id"));
                    proj.setTitle(rs.getString("title"));
                    proj.setDescription(rs.getString("description"));
                    proj.setLink(rs.getString("link"));
                    list.add(proj);
                }
            }
        }
        return list;
    }

    @Override
    public boolean addProject(Project proj) throws SQLException {
        String sql = "INSERT INTO projects (student_id, title, description, link) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proj.getStudentId());
            stmt.setString(2, proj.getTitle());
            stmt.setString(3, proj.getDescription());
            stmt.setString(4, proj.getLink());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteProject(int projectId) throws SQLException {
        String sql = "DELETE FROM projects WHERE project_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            return stmt.executeUpdate() > 0;
        }
    }

    private List<String> getSkillsList(int studentId) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT s.skill_name FROM skills s JOIN student_skills ss ON s.skill_id = ss.skill_id WHERE ss.student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("skill_name"));
                }
            }
        }
        return list;
    }

    @Override
    public boolean addSkill(int studentId, String skillName) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // First check if skill exists, if not add it
            String checkSkill = "SELECT skill_id FROM skills WHERE skill_name = ?";
            int skillId = -1;
            try (PreparedStatement stmt1 = conn.prepareStatement(checkSkill)) {
                stmt1.setString(1, skillName);
                try (ResultSet rs = stmt1.executeQuery()) {
                    if (rs.next()) skillId = rs.getInt("skill_id");
                }
            }
            if (skillId == -1) {
                String insertSkill = "INSERT INTO skills (skill_name) VALUES (?)";
                try (PreparedStatement stmt2 = conn.prepareStatement(insertSkill, Statement.RETURN_GENERATED_KEYS)) {
                    stmt2.setString(1, skillName);
                    stmt2.executeUpdate();
                    try (ResultSet rs = stmt2.getGeneratedKeys()) {
                        if (rs.next()) skillId = rs.getInt(1);
                    }
                }
            }
            
            // Link skill to student
            String linkSql = "INSERT IGNORE INTO student_skills (student_id, skill_id) VALUES (?, ?)";
            try (PreparedStatement stmt3 = conn.prepareStatement(linkSql)) {
                stmt3.setInt(1, studentId);
                stmt3.setInt(2, skillId);
                return stmt3.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean removeSkill(int studentId, String skillName) throws SQLException {
        String sql = "DELETE ss FROM student_skills ss JOIN skills s ON ss.skill_id = s.skill_id WHERE ss.student_id = ? AND s.skill_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, skillName);
            return stmt.executeUpdate() > 0;
        }
    }
}
