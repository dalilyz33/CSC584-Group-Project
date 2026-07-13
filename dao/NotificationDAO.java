package com.foodbank.dao;

import com.foodbank.model.NotificationBean;
import com.foodbank.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for the Notification table.
 * Notifications are inserted by system triggers (see NotificationHelper),
 * not authored manually by admin - so there is no "create category" or
 * "compose notification" flow here, only sending, listing, and read-status.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class NotificationDAO {

    // Insert a new notification (called by NotificationHelper trigger methods)
    public boolean sendNotification(NotificationBean notification) {
        String sql = "INSERT INTO Notification (type, recipient_id, recipient_role, message, date_sent, is_read) "
                + "VALUES (?, ?, ?, ?, NOW(), 0)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, notification.getType());
            if (notification.getRecipientId() != null) {
                ps.setInt(2, notification.getRecipientId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, notification.getRecipientRole());
            ps.setString(4, notification.getMessage());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Avoid spamming duplicate alerts (e.g. same low-stock message re-inserted every dashboard load)
    public boolean hasUnreadDuplicate(String type, String message) {
        String sql = "SELECT COUNT(*) FROM Notification WHERE type = ? AND message = ? AND is_read = 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, type);
            ps.setString(2, message);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get notifications relevant to a user: targeted directly at them, or broadcast to their role, or ALL
    public List<NotificationBean> getNotificationsForUser(int userId, String role) {
        List<NotificationBean> list = new ArrayList<>();
        String sql = "SELECT * FROM Notification "
                + "WHERE recipient_id = ? OR recipient_role = ? OR recipient_role = 'ALL' "
                + "ORDER BY date_sent DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, role);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean markAsRead(int notificationId) {
        String sql = "UPDATE Notification SET is_read = 1 WHERE notification_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, notificationId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mark every notification visible to this user as read (e.g. "mark all as read" button)
    public boolean markAllAsReadForUser(int userId, String role) {
        String sql = "UPDATE Notification SET is_read = 1 "
                + "WHERE (recipient_id = ? OR recipient_role = ? OR recipient_role = 'ALL') AND is_read = 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, role);
            return ps.executeUpdate() >= 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private NotificationBean mapRow(ResultSet rs) throws SQLException {
        NotificationBean n = new NotificationBean();
        n.setNotificationId(rs.getInt("notification_id"));
        n.setType(rs.getString("type"));
        int recipientId = rs.getInt("recipient_id");
        n.setRecipientId(rs.wasNull() ? null : recipientId);
        n.setRecipientRole(rs.getString("recipient_role"));
        n.setMessage(rs.getString("message"));
        n.setDateSent(rs.getTimestamp("date_sent"));
        n.setRead(rs.getBoolean("is_read"));
        return n;
    }
}
