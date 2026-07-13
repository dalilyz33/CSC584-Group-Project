package com.foodbank.util;

import com.foodbank.dao.NotificationDAO;
import com.foodbank.model.NotificationBean;

/**
 * Convenience trigger methods for system-generated notifications.
 * Other modules should call these directly instead of inserting into the
 * Notification table themselves, so message wording and type tags stay
 * consistent in one place. For example:
 *   - Iman's AssignVoucherServlet should call notifyVoucherAvailable(studentId, ...)
 *     after a voucher is assigned.
 *   - Lily's inventory update code could call notifyLowStock(...) / notifyExpiringSoon(...)
 *     directly when stock is updated, instead of relying only on the dashboard check.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class NotificationHelper {

    private static final NotificationDAO notificationDAO = new NotificationDAO();

    private NotificationHelper() {
        // static utility class
    }

    public static void notifyBookingAccepted(int studentId, String collectionDate, String collectionTime) {
        String message = "Your food collection booking for " + collectionDate
                + " (" + collectionTime + ") has been approved.";
        notificationDAO.sendNotification(new NotificationBean("BOOKING_ACCEPTED", studentId, "STUDENT", message));
    }

    public static void notifyBookingRejected(int studentId, String collectionDate, String collectionTime) {
        String message = "Your food collection booking for " + collectionDate
                + " (" + collectionTime + ") has been rejected.";
        notificationDAO.sendNotification(new NotificationBean("BOOKING_REJECTED", studentId, "STUDENT", message));
    }

    public static void notifyVoucherAvailable(int studentId, String voucherDescription) {
        String message = "A new voucher is available: " + voucherDescription;
        notificationDAO.sendNotification(new NotificationBean("VOUCHER_AVAILABLE", studentId, "STUDENT", message));
    }

    // Broadcast to all admins; skips if an identical unread alert already exists for this item
    public static void notifyLowStock(String itemName, int remainingQuantity) {
        String message = "Stock running low: " + itemName + " (" + remainingQuantity + " units remaining).";
        if (!notificationDAO.hasUnreadDuplicate("STOCK_LOW", message)) {
            notificationDAO.sendNotification(new NotificationBean("STOCK_LOW", null, "ADMIN", message));
        }
    }

    public static void notifyExpiringSoon(String itemName, String expiryDate) {
        String message = itemName + " is expiring soon (expiry date: " + expiryDate + ").";
        if (!notificationDAO.hasUnreadDuplicate("EXPIRING_SOON", message)) {
            notificationDAO.sendNotification(new NotificationBean("EXPIRING_SOON", null, "ADMIN", message));
        }
    }
}
