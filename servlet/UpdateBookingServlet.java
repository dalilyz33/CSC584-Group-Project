package com.foodbank.servlet;

import com.foodbank.dao.FoodCollectionBookingDAO;
import com.foodbank.model.FoodCollectionBookingBean;
import com.foodbank.util.NotificationHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles admin updating a booking's status (APPROVED, REJECTED, COMPLETED)
 * and automatically notifies the affected student via NotificationHelper.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class UpdateBookingServlet extends HttpServlet {

    private final FoodCollectionBookingDAO bookingDAO = new FoodCollectionBookingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equalsIgnoreCase(String.valueOf(session.getAttribute("role")))) {
            response.sendRedirect("login.jsp");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String newStatus = request.getParameter("status"); // APPROVED, REJECTED, COMPLETED

        boolean updated = bookingDAO.updateBookingStatus(bookingId, newStatus);

        if (updated) {
            FoodCollectionBookingBean booking = bookingDAO.getBookingById(bookingId);
            if (booking != null) {
                if ("APPROVED".equalsIgnoreCase(newStatus)) {
                    NotificationHelper.notifyBookingAccepted(
                            booking.getStudentId(), booking.getCollectionDate(), booking.getCollectionTime());
                } else if ("REJECTED".equalsIgnoreCase(newStatus)) {
                    NotificationHelper.notifyBookingRejected(
                            booking.getStudentId(), booking.getCollectionDate(), booking.getCollectionTime());
                }
            }
            request.setAttribute("successMessage", "Booking status updated successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to update booking status.");
        }

        response.sendRedirect("manageBooking.jsp");
    }
}
