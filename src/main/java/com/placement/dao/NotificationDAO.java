package com.placement.dao;

import com.placement.model.Notification;
import java.sql.SQLException;
import java.util.List;

public interface NotificationDAO {
    void create(Notification notification) throws SQLException;
    List<Notification> getByUserId(int userId) throws SQLException;
    int getUnreadCount(int userId) throws SQLException;
    boolean markRead(int notificationId, int userId) throws SQLException;
    boolean markAllRead(int userId) throws SQLException;
    boolean hasNewMatchNotification(int userId, int oppId) throws SQLException;
}
