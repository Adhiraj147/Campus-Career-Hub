package com.placement.dao;

import com.placement.model.Application;
import com.placement.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAOImpl implements ApplicationDAO {

    @Override
    public boolean apply(int studentId, int oppId) throws SQLException {
        if (hasApplied(studentId, oppId)) {
            return false;
        }
        String sql = "INSERT INTO applications (student_id, opp_id, status) VALUES (?, ?, 'APPLIED')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, oppId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean hasApplied(int studentId, int oppId) throws SQLException {
        String sql = "SELECT 1 FROM applications WHERE student_id = ? AND opp_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, oppId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Application> getApplicationsByStudent(int studentId) throws SQLException {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT a.*, o.title, c.company_name " +
                     "FROM applications a " +
                     "JOIN opportunities o ON a.opp_id = o.opp_id " +
                     "JOIN companies c ON o.company_id = c.company_id " +
                     "WHERE a.student_id = ? ORDER BY a.applied_at DESC";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Application app = new Application();
                    app.setApplicationId(rs.getInt("application_id"));
                    app.setStudentId(rs.getInt("student_id"));
                    app.setOppId(rs.getInt("opp_id"));
                    app.setStatus(rs.getString("status"));
                    app.setAppliedAt(rs.getTimestamp("applied_at"));
                    app.setOppTitle(rs.getString("title"));
                    app.setCompanyName(rs.getString("company_name"));
                    list.add(app);
                }
            }
        }
        return list;
    }

    @Override
    public List<Application> getApplicationsByOpportunity(int oppId) throws SQLException {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT a.*, s.first_name, s.last_name, s.branch, s.cgpa, s.resume_url " +
                     "FROM applications a " +
                     "JOIN students s ON a.student_id = s.student_id " +
                     "WHERE a.opp_id = ? ORDER BY a.applied_at ASC";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, oppId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Application app = new Application();
                    app.setApplicationId(rs.getInt("application_id"));
                    app.setStudentId(rs.getInt("student_id"));
                    app.setOppId(rs.getInt("opp_id"));
                    app.setStatus(rs.getString("status"));
                    app.setAppliedAt(rs.getTimestamp("applied_at"));
                    app.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    app.setStudentBranch(rs.getString("branch"));
                    app.setStudentCgpa(rs.getDouble("cgpa"));
                    app.setStudentResumeUrl(rs.getString("resume_url"));
                    list.add(app);
                }
            }
        }
        return list;
    }

    @Override
    public boolean updateStatus(int applicationId, String status, String remarks, int changedByUserId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // 1. Update application status
            String sql1 = "UPDATE applications SET status = ? WHERE application_id = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setString(1, status);
                stmt1.setInt(2, applicationId);
                stmt1.executeUpdate();
            }
            
            // 2. Insert into history
            String sql2 = "INSERT INTO application_status_history (application_id, status, remarks, changed_by) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                stmt2.setInt(1, applicationId);
                stmt2.setString(2, status);
                stmt2.setString(3, remarks);
                stmt2.setInt(4, changedByUserId);
                stmt2.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
