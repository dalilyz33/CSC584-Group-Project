package com.careshare.servlet;

import com.careshare.bean.NotificationBean;
import com.careshare.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ViewNotificationServlet")
public class ViewNotificationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<NotificationBean> notifications = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT n.*, c.category_name FROM Notification n JOIN NotificationCategory c ON n.notification_categoryId = c.category_id WHERE n.user_id = ? ORDER BY n.notification_dateSent DESC";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        NotificationBean n = new NotificationBean();
                        n.setNotificationId(rs.getInt("notification_id"));
                        n.setNotificationMessage(rs.getString("notification_message"));
                        n.setNotificationDateSent(rs.getTimestamp("notification_dateSent"));
                        n.setNotificationStatus(rs.getString("notification_status"));
                        n.setCategoryName(rs.getString("category_name"));
                        notifications.add(n);
                    }
                }
            }

            // Mark all fetched notifications as Read
            String updateSql = "UPDATE Notification SET notification_status = 'Read' WHERE user_id = ?";
            try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                psUpdate.setInt(1, userId);
                psUpdate.executeUpdate();
            }

            request.setAttribute("notifications", notifications);
            request.getRequestDispatcher("notificationList.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
