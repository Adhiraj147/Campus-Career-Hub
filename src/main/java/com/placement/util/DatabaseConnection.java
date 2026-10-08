package com.placement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database configuration
   private static final String URL =
        "jdbc:mysql://mysql-2bcfb9f-angadsinghshekhawat8-2f66.b.aivencloud.com:18967/defaultdb?sslMode=REQUIRED&serverTimezone=UTC";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_-YGmq2j6D8TOXxABPkz"; // Update this as per your local mysql configuration

    // Load driver class exactly once
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC Driver.");
        }
    }

    /**
     * Gets a connection to the database.
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
