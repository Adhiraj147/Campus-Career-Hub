package com.placement.dao;

import com.placement.model.Company;
import com.placement.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDAOImpl implements CompanyDAO {

    @Override
    public Company getCompanyByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM companies WHERE user_id = ?";
        return getCompany(sql, userId);
    }

    @Override
    public Company getCompanyById(int companyId) throws SQLException {
        String sql = "SELECT * FROM companies WHERE company_id = ?";
        return getCompany(sql, companyId);
    }

    private Company getCompany(String sql, int param) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, param);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Company c = new Company();
                    c.setCompanyId(rs.getInt("company_id"));
                    c.setUserId(rs.getInt("user_id"));
                    c.setCompanyName(rs.getString("company_name"));
                    c.setDescription(rs.getString("description"));
                    c.setWebsite(rs.getString("website"));
                    c.setIndustry(rs.getString("industry"));
                    c.setLocation(rs.getString("location"));
                    c.setContactPerson(rs.getString("contact_person"));
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public boolean updateCompanyProfile(Company company) throws SQLException {
        String sql = "UPDATE companies SET company_name=?, description=?, website=?, industry=?, location=?, contact_person=? WHERE company_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, company.getCompanyName());
            stmt.setString(2, company.getDescription());
            stmt.setString(3, company.getWebsite());
            stmt.setString(4, company.getIndustry());
            stmt.setString(5, company.getLocation());
            stmt.setString(6, company.getContactPerson());
            stmt.setInt(7, company.getCompanyId());
            return stmt.executeUpdate() > 0;
        }
    }
}
