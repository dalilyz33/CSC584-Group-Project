package com.careshare.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.careshare.bean.UserBean;
import com.careshare.dao.AdminDao;
import com.careshare.dao.DonorDao;
import com.careshare.dao.StudentDao;
import com.careshare.dao.UserDao;

public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Step 1: Read the email and password submitted from login.jsp.
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Step 2: Basic validation before even touching the database — if either
        // field is empty there is no point running a query.
        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Email and password are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Step 3: Check the credentials against the AppUser table.
        UserDao userDao = new UserDao();
        UserBean loggedInUser = userDao.validateLogin(email, password);

        // validateLogin() returns null if no row matched the email/password pair —
        // we don't tell the user which one was wrong, since revealing "email exists
        // but password is wrong" gives attackers information they can use to guess
        // valid emails.
        if (loggedInUser == null) {
            request.setAttribute("errorMessage", "Invalid email or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Step 4: Credentials are valid — create a session. request.getSession()
        // with no arguments returns the existing session if one already exists,
        // or creates a brand new one if it doesn't.
        HttpSession session = request.getSession();

        // Store the core identity details every role needs, regardless of whether
        // they're a Student, Admin, or Donor.
        session.setAttribute("userId", loggedInUser.getUserId());
        session.setAttribute("userFullName", loggedInUser.getUserFullName());
        session.setAttribute("userRole", loggedInUser.getUserRole());

        // Step 5: Look up and store the role-specific ID too, using the matching
        // DAO based on which role this user has. Other members' servlets (e.g.
        // Member 4's booking servlet) will read "studentId" back out of the
        // session later, so it needs to be set here, right after login.
        String role = loggedInUser.getUserRole();

        if (role.equals("Student")) {
            StudentDao studentDao = new StudentDao();
            String studentId = studentDao.getStudentIdByUserId(loggedInUser.getUserId());
            session.setAttribute("studentId", studentId);

        } else if (role.equals("Admin")) {
            AdminDao adminDao = new AdminDao();
            String staffId = adminDao.getStaffIdByUserId(loggedInUser.getUserId());
            session.setAttribute("staffId", staffId);

        } else if (role.equals("Donor")) {
            DonorDao donorDao = new DonorDao();
            String donorId = donorDao.getDonorIdByUserId(loggedInUser.getUserId());
            session.setAttribute("donorId", donorId);
        }

        // Step 6: Hand off to DashboardServlet, which reads userRole from the
        // session and forwards to the correct dashboard JSP. Using sendRedirect()
        // here (not forward()) causes a fresh browser request to DashboardServlet,
        // so refreshing the dashboard page later re-runs DashboardServlet's logic
        // rather than re-submitting the login form.
        response.sendRedirect("DashboardServlet");
    }

    // Same doGet/doPost-calls-processRequest pattern as RegisterServlet. The
    // login.jsp form should use method="post" since it submits a password.
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