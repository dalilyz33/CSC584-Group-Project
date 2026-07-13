package com.foodbank.servlet;

import com.foodbank.dao.FoodCollectionBookingDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles a student cancelling their own food collection booking.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class CancelBookingServlet extends HttpServlet {

    private final FoodCollectionBookingDAO bookingDAO = new FoodCollectionBookingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int studentId = (int) session.getAttribute("studentId");
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        boolean cancelled = bookingDAO.cancelBooking(bookingId, studentId);

        if (cancelled) {
            request.setAttribute("successMessage", "Booking cancelled successfully.");
        } else {
            request.setAttribute("errorMessage", "Unable to cancel this booking. It may already be completed or cancelled.");
        }

        response.sendRedirect("myBooking.jsp");
    }
}
