package com.placement.model;

import java.sql.Timestamp;

public class Notification {
    private int notificationId;
    private int userId;
    private String message;
    private String notificationType; // 'APP_STATUS', 'NEW_MATCH', 'GENERAL'
    private Integer relatedOppId;
    private Integer relatedAppId;
    private boolean read;
    private Timestamp createdAt;

    public Notification() {}

    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }

    public Integer getRelatedOppId() { return relatedOppId; }
    public void setRelatedOppId(Integer relatedOppId) { this.relatedOppId = relatedOppId; }

    public Integer getRelatedAppId() { return relatedAppId; }
    public void setRelatedAppId(Integer relatedAppId) { this.relatedAppId = relatedAppId; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
