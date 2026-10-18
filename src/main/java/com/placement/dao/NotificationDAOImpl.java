package com.placement.dao;

import com.placement.model.Notification;
import com.placement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO {

    @Override
    public void create(Notification n) throws SQLException {
        String sql = "INSERT INTO notifications (user_id, message, notification_type, related_opp_id, related_app_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, n.getUserId());
            stmt.setString(2, n.getMessage());
            stmt.setString(3, n.getNotificationType() != null ? n.getNotificationType() : "GENERAL");
            if (n.getRelatedOppId() != null) stmt.setInt(4, n.getRelatedOppId());
            else stmt.setNull(4, Types.INTEGER);
            if (n.getRelatedAppId() != null) stmt.setInt(5, n.getRelatedAppId());
            else stmt.setNull(5, Types.INTEGER);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Notification> getByUserId(int userId) throws SQLException {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC LIMIT 50";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    @Override
    public int getUnreadCount(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM notifications WHERE user_id = ? AND is_read = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    @Override
    public boolean markRead(int notificationId, int userId) throws SQLException {
        String sql = "UPDATE notifications SET is_read = 1 WHERE notification_id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean markAllRead(int userId) throws SQLException {
        String sql = "UPDATE notifications SET is_read = 1 WHERE user_id = ? AND is_read = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean hasNewMatchNotification(int userId, int oppId) throws SQLException {
        String sql = "SELECT 1 FROM notifications WHERE user_id = ? AND related_opp_id = ? AND notification_type = 'NEW_MATCH'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, oppId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Notification mapRow(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setNotificationId(rs.getInt("notification_id"));
        n.setUserId(rs.getInt("user_id"));
        n.setMessage(rs.getString("message"));
        n.setNotificationType(rs.getString("notification_type"));
        int oppId = rs.getInt("related_opp_id");
        n.setRelatedOppId(rs.wasNull() ? null : oppId);
        int appId = rs.getInt("related_app_id");
        n.setRelatedAppId(rs.wasNull() ? null : appId);
        n.setRead(rs.getBoolean("is_read"));
        n.setCreatedAt(rs.getTimestamp("created_at"));
        return n;
    }
}
