package com.careshare.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class NotificationBean implements Serializable {
    private int notificationId;
    private int userId;
    private String notificationMessage;
    private Timestamp notificationDateSent;
    private String notificationStatus;
    private int notificationCategoryId;
    
    // Display properties
    private String categoryName;

    public NotificationBean() {}

    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getNotificationMessage() { return notificationMessage; }
    public void setNotificationMessage(String notificationMessage) { this.notificationMessage = notificationMessage; }
    public Timestamp getNotificationDateSent() { return notificationDateSent; }
    public void setNotificationDateSent(Timestamp notificationDateSent) { this.notificationDateSent = notificationDateSent; }
    public String getNotificationStatus() { return notificationStatus; }
    public void setNotificationStatus(String notificationStatus) { this.notificationStatus = notificationStatus; }
    public int getNotificationCategoryId() { return notificationCategoryId; }
    public void setNotificationCategoryId(int notificationCategoryId) { this.notificationCategoryId = notificationCategoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
}
