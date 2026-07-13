package com.careshare.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // request.getSession(false) returns the existing session, or null if there
        // isn't one. We use false (not the no-argument version) because there is no
        // reason to create a brand new session just to immediately destroy it.
        HttpSession session = request.getSession(false);

        // Only invalidate if a session actually exists — calling invalidate() on a
        // session that's already gone would throw an IllegalStateException.
        if (session != null) {
            // invalidate() destroys the session and removes every attribute stored
            // in it (userId, userRole, studentId, etc.), which is what actually logs
            // the user out. Simply "forgetting" the variable in Java would do
            // nothing, since the session lives on the server, tied to the browser's
            // session cookie, not to any Java variable in this method.
            session.invalidate();
        }

        // Send the user back to the login page after logging out. sendRedirect()
        // is used (not forward()) so the browser's address bar updates and a page
        // refresh doesn't attempt to re-run any leftover POST data.
        response.sendRedirect("login.jsp");
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