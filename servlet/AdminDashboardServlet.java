package com.foodbank.servlet;

import com.foodbank.dao.DashboardDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Loads aggregated statistics for the admin dashboard landing page.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class AdminDashboardServlet extends HttpServlet {

    private final DashboardDAO dashboardDAO = new DashboardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equalsIgnoreCase(String.valueOf(session.getAttribute("role")))) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Fire any low-stock / expiring-soon alerts to admin before loading the page
        dashboardDAO.checkInventoryAlerts();

        Map<String, Integer> stats = dashboardDAO.getDashboardStats();
        request.setAttribute("stats", stats);
        request.setAttribute("inventoryList", dashboardDAO.getInventoryList());
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }
}
