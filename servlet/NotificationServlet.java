package com.foodbank.servlet;

import com.foodbank.dao.NotificationDAO;
import com.foodbank.model.NotificationBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Lists notifications for the logged-in user (student or admin) and lets
 * them mark notifications as read. Notifications themselves are created
 * by system triggers (see com.foodbank.util.NotificationHelper), not
 * authored here.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class NotificationServlet extends HttpServlet {

    private final NotificationDAO notificationDAO = new NotificationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        List<NotificationBean> notifications = notificationDAO.getNotificationsForUser(userId, role);
        request.setAttribute("notifications", notifications);
        request.getRequestDispatcher("notification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");
        String action = request.getParameter("action");

        if ("markRead".equalsIgnoreCase(action)) {
            int notificationId = Integer.parseInt(request.getParameter("notificationId"));
            notificationDAO.markAsRead(notificationId);

        } else if ("markAllRead".equalsIgnoreCase(action)) {
            notificationDAO.markAllAsReadForUser(userId, role);
        }

        response.sendRedirect("NotificationServlet");
    }
}
