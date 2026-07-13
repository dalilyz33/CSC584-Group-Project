package com.foodbank.servlet;

import com.foodbank.dao.FoodCollectionBookingDAO;
import com.foodbank.model.FoodCollectionBookingBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Displays bookings:
 * - Student role -> only their own bookings (myBooking.jsp)
 * - Admin role -> all bookings, optionally filtered by status (manageBooking.jsp)
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class ViewBookingServlet extends HttpServlet {

    private final FoodCollectionBookingDAO bookingDAO = new FoodCollectionBookingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");

        if ("STUDENT".equalsIgnoreCase(role)) {
            int studentId = (int) session.getAttribute("studentId");
            List<FoodCollectionBookingBean> bookings = bookingDAO.getBookingsByStudent(studentId);
            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("myBooking.jsp").forward(request, response);

        } else if ("ADMIN".equalsIgnoreCase(role)) {
            String statusFilter = request.getParameter("status");
            List<FoodCollectionBookingBean> bookings;

            if (statusFilter != null && !statusFilter.trim().isEmpty() && !statusFilter.equalsIgnoreCase("ALL")) {
                bookings = bookingDAO.getBookingsByStatus(statusFilter);
            } else {
                bookings = bookingDAO.getAllBookings();
            }

            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("manageBooking.jsp").forward(request, response);

        } else {
            response.sendRedirect("index.jsp");
        }
    }
}
