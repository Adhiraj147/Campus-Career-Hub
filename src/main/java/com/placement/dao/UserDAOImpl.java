package com.placement.dao;

import com.placement.model.User;
import com.placement.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAOImpl implements UserDAO {

    @Override
    public int registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (email, password_hash, role, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        }
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    user.setStatus(rs.getString("status"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public boolean createStudentProfile(int userId, String firstName, String lastName, String rollNo) throws SQLException {
        String sql = "INSERT INTO students (user_id, first_name, last_name, roll_no) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, rollNo);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean createCompanyProfile(int userId, String companyName) throws SQLException {
        String sql = "INSERT INTO companies (user_id, company_name) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setString(2, companyName);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }


    @Override
    public int registerStudentWithTransaction(User user, String firstName, String lastName, String rollNo) throws SQLException {
        String insertUserSql = "INSERT INTO users (email, password_hash, role, status) VALUES (?, ?, ?, ?)";
        String insertStudentSql = "INSERT INTO students (user_id, first_name, last_name, roll_no) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement userStmt = null;
        PreparedStatement studentStmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // BEGIN TRANSACTION
            
            userStmt = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, user.getEmail());
            userStmt.setString(2, user.getPasswordHash());
            userStmt.setString(3, user.getRole());
            userStmt.setString(4, user.getStatus());
            
            int affectedRows = userStmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return -1;
            }
            
            int newUserId = -1;
            generatedKeys = userStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                newUserId = generatedKeys.getInt(1);
            } else {
                conn.rollback();
                return -1;
            }
            
            studentStmt = conn.prepareStatement(insertStudentSql);
            studentStmt.setInt(1, newUserId);
            studentStmt.setString(2, firstName);
            studentStmt.setString(3, lastName);
            studentStmt.setString(4, rollNo);
            
            studentStmt.executeUpdate();
            
            conn.commit(); // COMMIT
            return newUserId;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // ROLLBACK on any failure (like duplicate rollNo)
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e; // Rethrow so RegisterServlet can inspect the SQLState
        } finally {
            if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException e) {}
            if (userStmt != null) try { userStmt.close(); } catch (SQLException e) {}
            if (studentStmt != null) try { studentStmt.close(); } catch (SQLException e) {}
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public int registerRecruiterWithTransaction(User user, String companyName) throws SQLException {
        String insertUserSql = "INSERT INTO users (email, password_hash, role, status) VALUES (?, ?, ?, ?)";
        String insertCompanySql = "INSERT INTO companies (user_id, company_name) VALUES (?, ?)";
        
        Connection conn = null;
        PreparedStatement userStmt = null;
        PreparedStatement companyStmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // BEGIN TRANSACTION
            
            userStmt = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, user.getEmail());
            userStmt.setString(2, user.getPasswordHash());
            userStmt.setString(3, user.getRole());
            userStmt.setString(4, user.getStatus());
            
            int affectedRows = userStmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return -1;
            }
            
            int newUserId = -1;
            generatedKeys = userStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                newUserId = generatedKeys.getInt(1);
            } else {
                conn.rollback();
                return -1;
            }
            
            companyStmt = conn.prepareStatement(insertCompanySql);
            companyStmt.setInt(1, newUserId);
            companyStmt.setString(2, companyName);
            
            companyStmt.executeUpdate();
            
            conn.commit(); // COMMIT
            return newUserId;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException e) {}
            if (userStmt != null) try { userStmt.close(); } catch (SQLException e) {}
            if (companyStmt != null) try { companyStmt.close(); } catch (SQLException e) {}
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
}
