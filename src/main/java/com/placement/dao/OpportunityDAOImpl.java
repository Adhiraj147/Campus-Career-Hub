package com.placement.dao;

import com.placement.model.Opportunity;
import com.placement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpportunityDAOImpl implements OpportunityDAO {

    @Override
    public boolean addOpportunity(Opportunity opp) throws SQLException {
        String sql = "INSERT INTO opportunities (company_id, title, type, description, location, work_mode, salary_stipend, min_cgpa, eligible_branches, graduation_year_req, required_skills, deadline) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, opp.getCompanyId());
            stmt.setString(2, opp.getTitle());
            stmt.setString(3, opp.getType());
            stmt.setString(4, opp.getDescription());
            stmt.setString(5, opp.getLocation());
            stmt.setString(6, opp.getWorkMode());
            stmt.setString(7, opp.getSalaryStipend());
            stmt.setDouble(8, opp.getMinCgpa());
            stmt.setString(9, opp.getEligibleBranches());
            stmt.setInt(10, opp.getGraduationYearReq());
            stmt.setString(11, opp.getRequiredSkills());
            stmt.setDate(12, opp.getDeadline());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateOpportunity(Opportunity opp) throws SQLException {
        String sql = "UPDATE opportunities SET title=?, type=?, description=?, location=?, work_mode=?, salary_stipend=?, min_cgpa=?, eligible_branches=?, graduation_year_req=?, required_skills=?, deadline=? WHERE opp_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, opp.getTitle());
            stmt.setString(2, opp.getType());
            stmt.setString(3, opp.getDescription());
            stmt.setString(4, opp.getLocation());
            stmt.setString(5, opp.getWorkMode());
            stmt.setString(6, opp.getSalaryStipend());
            stmt.setDouble(7, opp.getMinCgpa());
            stmt.setString(8, opp.getEligibleBranches());
            stmt.setInt(9, opp.getGraduationYearReq());
            stmt.setString(10, opp.getRequiredSkills());
            stmt.setDate(11, opp.getDeadline());
            stmt.setInt(12, opp.getOppId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean closeOpportunity(int oppId) throws SQLException {
        String sql = "UPDATE opportunities SET status='CLOSED' WHERE opp_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, oppId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Opportunity getOpportunityById(int oppId) throws SQLException {
        String sql = "SELECT o.*, c.company_name FROM opportunities o JOIN companies c ON o.company_id = c.company_id WHERE o.opp_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, oppId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToOpportunity(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Opportunity> getOpportunitiesByCompany(int companyId) throws SQLException {
        List<Opportunity> list = new ArrayList<>();
        String sql = "SELECT o.*, c.company_name FROM opportunities o JOIN companies c ON o.company_id = c.company_id WHERE o.company_id = ? ORDER BY o.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToOpportunity(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<Opportunity> getAllOpenOpportunities() throws SQLException {
        List<Opportunity> list = new ArrayList<>();
        String sql = "SELECT o.*, c.company_name FROM opportunities o JOIN companies c ON o.company_id = c.company_id WHERE o.status = 'OPEN' ORDER BY o.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToOpportunity(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<Opportunity> searchOpportunities(String keyword, String type) throws SQLException {
        List<Opportunity> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT o.*, c.company_name FROM opportunities o JOIN companies c ON o.company_id = c.company_id WHERE o.status = 'OPEN'");
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (o.title LIKE ? OR c.company_name LIKE ? OR o.required_skills LIKE ?)");
        }
        if (type != null && !type.isEmpty() && !type.equals("ALL")) {
            sql.append(" AND o.type = ?");
        }
        
        sql.append(" ORDER BY o.created_at DESC");
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchPattern = "%" + keyword.trim() + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }
            if (type != null && !type.isEmpty() && !type.equals("ALL")) {
                stmt.setString(paramIndex, type);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToOpportunity(rs));
                }
            }
        }
        return list;
    }

    private Opportunity mapRowToOpportunity(ResultSet rs) throws SQLException {
        Opportunity opp = new Opportunity();
        opp.setOppId(rs.getInt("opp_id"));
        opp.setCompanyId(rs.getInt("company_id"));
        opp.setCompanyName(rs.getString("company_name"));
        opp.setTitle(rs.getString("title"));
        opp.setType(rs.getString("type"));
        opp.setDescription(rs.getString("description"));
        opp.setLocation(rs.getString("location"));
        opp.setWorkMode(rs.getString("work_mode"));
        opp.setSalaryStipend(rs.getString("salary_stipend"));
        opp.setMinCgpa(rs.getDouble("min_cgpa"));
        opp.setEligibleBranches(rs.getString("eligible_branches"));
        opp.setGraduationYearReq(rs.getInt("graduation_year_req"));
        opp.setRequiredSkills(rs.getString("required_skills"));
        opp.setDeadline(rs.getDate("deadline"));
        opp.setStatus(rs.getString("status"));
        opp.setCreatedAt(rs.getTimestamp("created_at"));
        return opp;
    }
}
