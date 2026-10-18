package com.placement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides database connections using credentials read from environment variables.
 *
 * Required environment variables:
 *   DB_URL       — full JDBC URL  e.g. jdbc:mysql://host:port/dbname?sslMode=REQUIRED&serverTimezone=UTC
 *   DB_USERNAME  — database username
 *   DB_PASSWORD  — database password
 *
 * The application will fail fast (at class-load time) if any variable is missing
 * so the error is obvious in logs immediately on startup.
 * Credentials are NEVER printed to logs or stack traces.
 */
public class DatabaseConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        // ── Load and validate env vars at startup ─────────────────────────
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found on classpath.", e);
        }

        URL      = requireEnv("DB_URL");
        USER     = requireEnv("DB_USERNAME");
        PASSWORD = requireEnv("DB_PASSWORD");
    }

    /**
     * Returns a live connection to the database.
     * Throws SQLException on network/auth failure — never leaks credentials in the message.
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Re-throw with a safe message — strip any driver message that might echo the URL/password
            throw new SQLException(
                "Database connection failed. Check DB_URL, DB_USERNAME and DB_PASSWORD environment variables. " +
                "SQLState=" + e.getSQLState() + ", ErrorCode=" + e.getErrorCode(), e.getSQLState(), e.getErrorCode(), e);
        }
    }

    // ── Helper ─────────────────────────────────────────────────────────────

    /**
     * Reads an environment variable.  Throws a clear RuntimeException (without
     * printing the value) if the variable is missing or blank.
     */
    private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(
                "[CONFIGURATION ERROR] Required environment variable \"" + name + "\" is not set or is empty. " +
                "Set it before starting the application. See README.md for instructions."
            );
        }
        return value;
    }
}
