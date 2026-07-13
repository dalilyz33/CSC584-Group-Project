package com.careshare.servlet;

import com.careshare.bean.FoodCollectionBookingBean;
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

@WebServlet("/ViewBookingServlet")
public class ViewBookingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<FoodCollectionBookingBean> bookings = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            if ("myBookings".equals(action)) {
                HttpSession session = request.getSession();
                Integer studentId = (Integer) session.getAttribute("userId");
                
                String sql = "SELECT b.*, f.foodItem_name FROM FoodCollectionBooking b JOIN FoodItem f ON b.booking_foodItem_id = f.foodItem_id WHERE b.booking_studentId = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, studentId);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            FoodCollectionBookingBean b = new FoodCollectionBookingBean();
                            b.setBookingId(rs.getInt("bookind_id")); // matches DB spelling mapping
                            b.setBookingDate(rs.getDate("booking_date"));
                            b.setBookingTime(rs.getTime("booking_time"));
                            b.setFoodItemName(rs.getString("foodItem_name"));
                            bookings.add(b);
                        }
                    }
                }
                request.setAttribute("myBookings", bookings);
                request.getRequestDispatcher("myFoodBooking.jsp").forward(request, response);
                
            } else if ("adminView".equals(action)) {
                String sql = "SELECT b.*, f.foodItem_name, u.user_fullName FROM FoodCollectionBooking b JOIN FoodItem f ON b.booking_foodItem_id = f.foodItem_id JOIN User u ON b.booking_studentId = u.user_id";
                try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        FoodCollectionBookingBean b = new FoodCollectionBookingBean();
                        b.setBookingId(rs.getInt("bookind_id"));
                        b.setBookingDate(rs.getDate("booking_date"));
                        b.setBookingTime(rs.getTime("booking_time"));
                        b.setFoodItemName(rs.getString("foodItem_name"));
                        b.setStudentName(rs.getString("user_fullName"));
                        bookings.add(b);
                    }
                }
                request.setAttribute("allBookings", bookings);
                request.getRequestDispatcher("adminViewBookings.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
