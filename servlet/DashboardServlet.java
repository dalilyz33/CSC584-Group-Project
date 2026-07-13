package com.careshare.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DashboardServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // request.getSession(false) is different from request.getSession() used in
        // LoginServlet — the "false" means "give me the existing session, or null
        // if there isn't one." We use false here because this servlet should never
        // create a brand new empty session; if there's no session, that means the
        // person never logged in, and we want to detect that.
        HttpSession session = request.getSession(false);

        // Check both that a session exists AND that it has a userRole attribute.
        // A session could technically exist (e.g. from an earlier visit) without
        // ever having been through LoginServlet, so checking userRole specifically
        // confirms this person actually logged in successfully.
        if (session == null || session.getAttribute("userRole") == null) {
            // Not logged in — send them to the login page instead of a dashboard.
            response.sendRedirect("login.jsp");
            return;
        }

        // Read back the role that LoginServlet stored in the session.
        String role = (String) session.getAttribute("userRole");

        // Step: forward (not redirect) to the correct dashboard JSP based on role.
        // forward() is used here because we already have the session data we need
        // in this request — there's no reason to make the browser send a second
        // round-trip request the way sendRedirect() would.
        if (role.equals("Student")) {
            request.getRequestDispatcher("studentDashboard.jsp").forward(request, response);

        } else if (role.equals("Admin")) {
            request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);

        } else if (role.equals("Donor")) {
            request.getRequestDispatcher("donorDashboard.jsp").forward(request, response);

        } else {
            // Defensive fallback: if userRole somehow has an unexpected value (data
            // corruption, manual database edit, etc.), don't guess — send them back
            // to login rather than forwarding to a JSP that expects a role we don't
            // recognize.
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}