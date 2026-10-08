package com.placement.dao;

import com.placement.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public Map<String, Integer> getSystemStatistics() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            stats.put("totalStudents", getCount(conn, "SELECT COUNT(*) FROM students"));
            stats.put("totalCompanies", getCount(conn, "SELECT COUNT(*) FROM companies"));
            stats.put("totalJobs", getCount(conn, "SELECT COUNT(*) FROM opportunities WHERE type='JOB'"));
            stats.put("totalInternships", getCount(conn, "SELECT COUNT(*) FROM opportunities WHERE type='INTERNSHIP'"));
            stats.put("totalApplications", getCount(conn, "SELECT COUNT(*) FROM applications"));
            stats.put("totalSelected", getCount(conn, "SELECT COUNT(*) FROM applications WHERE status='SELECTED'"));
        }
        
        return stats;
    }
    
    private int getCount(Connection conn, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
