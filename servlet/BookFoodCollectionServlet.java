package com.careshare.servlet;

import com.careshare.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BookFoodCollectionServlet")
public class BookFoodCollectionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer studentId = (Integer) session.getAttribute("userId");
        
        if (studentId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int foodItemId = Integer.parseInt(request.getParameter("foodItemId"));
        String dateStr = request.getParameter("bookingDate");
        String timeStr = request.getParameter("bookingTime") + ":00";
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO FoodCollectionBooking (booking_date, booking_time, booking_foodItem_id, booking_studentId, category_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, Date.valueOf(dateStr));
                ps.setTime(2, Time.valueOf(timeStr));
                ps.setInt(3, foodItemId);
                ps.setInt(4, studentId);
                ps.setInt(5, categoryId);
                ps.executeUpdate();
            }
            
            // Auto System Notification
            String notifSql = "INSERT INTO Notification (user_id, notification_message, notification_dateSent, notification_status, notification_categoryId) VALUES (?, ?, CURRENT_TIMESTAMP, 'Unread', ?)";
            try (PreparedStatement psNotif = conn.prepareStatement(notifSql)) {
                psNotif.setInt(1, studentId);
                psNotif.setString(2, "Your food collection booking for date " + dateStr + " has been registered successfully.");
                psNotif.setInt(3, categoryId);
                psNotif.executeUpdate();
            }
            
            response.sendRedirect("ViewBookingServlet?action=myBookings");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
