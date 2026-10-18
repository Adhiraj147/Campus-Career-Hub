package com.placement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * One-time / idempotent DB migration utility.
 *
 * Creates the notifications table if it does not already exist.
 * Reads credentials from the same environment variables used by the application:
 *   DB_URL, DB_USERNAME, DB_PASSWORD
 *
 * Run manually:
 *   mvn exec:java -Dexec.mainClass=com.placement.util.DbMigration -Dexec.classpathScope=compile
 *
 * In Docker this is called automatically by start.sh before Tomcat boots.
 */
public class DbMigration {

    public static void main(String[] args) throws Exception {
        String url      = requireEnv("DB_URL");
        String user     = requireEnv("DB_USERNAME");
        String password = requireEnv("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement  stmt = conn.createStatement()) {

            System.out.println("[DbMigration] Ensuring notifications table exists...");
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS notifications (" +
                "  notification_id   INT AUTO_INCREMENT PRIMARY KEY," +
                "  user_id           INT NOT NULL," +
                "  message           TEXT NOT NULL," +
                "  notification_type VARCHAR(20) NOT NULL DEFAULT 'GENERAL'," +
                "  related_opp_id    INT DEFAULT NULL," +
                "  related_app_id    INT DEFAULT NULL," +
                "  is_read           TINYINT(1) NOT NULL DEFAULT 0," +
                "  created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE," +
                "  FOREIGN KEY (related_opp_id) REFERENCES opportunities(opp_id) ON DELETE SET NULL," +
                "  FOREIGN KEY (related_app_id) REFERENCES applications(application_id) ON DELETE SET NULL," +
                "  INDEX idx_user_read (user_id, is_read)," +
                "  INDEX idx_created (created_at)" +
                ")"
            );

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM notifications");
            rs.next();
            System.out.println("[DbMigration] Done. notifications table row count: " + rs.getInt(1));
        }
    }

    private static String requireEnv(String name) {
        String v = System.getenv(name);
        if (v == null || v.trim().isEmpty()) {
            throw new RuntimeException(
                "[DbMigration] Required environment variable \"" + name + "\" is not set."
            );
        }
        return v;
    }
}
