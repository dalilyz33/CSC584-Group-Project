package com.foodbank.model;

import java.sql.Timestamp;

/**
 * JavaBean representing a row in the Notification table.
 * Notifications are triggered automatically by system events (not manually
 * authored by admin), identified by a simple "type" tag:
 *   - BOOKING_ACCEPTED   -> sent to a student when their booking is approved
 *   - BOOKING_REJECTED   -> sent to a student when their booking is rejected
 *   - VOUCHER_AVAILABLE  -> sent to a student when a new voucher is assigned
 *   - STOCK_LOW          -> sent to admin when an inventory item runs low
 *   - EXPIRING_SOON      -> sent to admin when an inventory item is close to expiry
 *
 * A notification can target a specific user (recipientId) or a whole role
 * group (recipientRole = STUDENT / DONOR / ADMIN / ALL).
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class NotificationBean {

    private int notificationId;
    private String type;
    private Integer recipientId;   // nullable - specific user id
    private String recipientRole;  // STUDENT, DONOR, ADMIN, ALL
    private String message;
    private Timestamp dateSent;
    private boolean isRead;

    public NotificationBean() {
    }

    public NotificationBean(String type, Integer recipientId, String recipientRole, String message) {
        this.type = type;
        this.recipientId = recipientId;
        this.recipientRole = recipientRole;
        this.message = message;
        this.isRead = false;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientRole() {
        return recipientRole;
    }

    public void setRecipientRole(String recipientRole) {
        this.recipientRole = recipientRole;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDateSent() {
        return dateSent;
    }

    public void setDateSent(Timestamp dateSent) {
        this.dateSent = dateSent;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
