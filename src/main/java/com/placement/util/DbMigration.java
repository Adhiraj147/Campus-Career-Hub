package com.placement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * One-time migration: creates (or fixes) the notifications table.
 * Run with: mvn exec:java -Dexec.mainClass=com.placement.util.DbMigration
 */
public class DbMigration {

    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://mysql-2bcfb9f-angadsinghshekhawat8-2f66.b.aivencloud.com:18967/defaultdb"
                   + "?sslMode=REQUIRED&serverTimezone=UTC"
                   + "&connectTimeout=15000&socketTimeout=60000";
        String user     = "avnadmin";
        String password = "AVNS_-YGmq2j6D8TOXxABPkz";

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement  stmt = conn.createStatement()) {

            // Drop and recreate to ensure correct schema
            System.out.println("[DbMigration] Dropping old notifications table if exists...");
            stmt.execute("DROP TABLE IF EXISTS notifications");

            System.out.println("[DbMigration] Creating notifications table...");
            stmt.execute(
                "CREATE TABLE notifications (" +
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

            // Verify
            ResultSet rs = stmt.executeQuery("SHOW COLUMNS FROM notifications");
            System.out.println("[DbMigration] SUCCESS — notifications table columns:");
            while (rs.next()) {
                System.out.println("  " + rs.getString("Field") + " " + rs.getString("Type"));
            }
        }
        System.out.println("[DbMigration] Done.");
    }
}
