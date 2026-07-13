package com.careshare.servlet;

import com.careshare.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SendNotificationServlet")
public class SendNotificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleTarget = request.getParameter("roleTarget"); // Admin broadcasting tool
        String message = request.getParameter("notificationMessage");
        int categoryId = Integer.parseInt(request.getParameter("notificationCategoryId"));

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Notification (user_id, notification_message, notification_dateSent, notification_status, notification_categoryId) " +
                         "SELECT user_id, ?, CURRENT_TIMESTAMP, 'Unread', ? FROM User WHERE user_role = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, message);
                ps.setInt(2, categoryId);
                ps.setString(3, roleTarget);
                ps.executeUpdate();
            }
            response.sendRedirect("sendNotification.jsp?status=success");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
