package com.foodbank.servlet;

import com.foodbank.dao.FoodCollectionBookingDAO;
import com.foodbank.model.FoodCollectionBookingBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles a student submitting a new food collection booking.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class BookFoodServlet extends HttpServlet {

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
        String collectionDate = request.getParameter("collectionDate");
        String collectionTime = request.getParameter("collectionTime");
        String remarks = request.getParameter("remarks");

        if (collectionDate == null || collectionDate.trim().isEmpty()
                || collectionTime == null || collectionTime.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Collection date and time are required.");
            request.getRequestDispatcher("foodBooking.jsp").forward(request, response);
            return;
        }

        FoodCollectionBookingBean booking = new FoodCollectionBookingBean(studentId, collectionDate, collectionTime, remarks);
        boolean success = bookingDAO.createBooking(booking);

        if (success) {
            request.setAttribute("successMessage", "Food collection booking submitted successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to submit booking. Please try again.");
        }

        response.sendRedirect("myBooking.jsp");
    }
}
