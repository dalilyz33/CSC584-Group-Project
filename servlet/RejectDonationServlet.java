package com.careshare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

import com.careshare.dao.DonationDAO;

@WebServlet(name = "RejectDonationServlet", urlPatterns = {"/RejectDonationServlet"})
public class RejectDonationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String donationID = request.getParameter("donationID");
            String staffID= request.getParameter("staffID");
            Date today = new Date(System.currentTimeMillis());

            DonationDAO donationDAO = new DonationDAO();
            boolean success = donationDAO.rejectDonation(donationID, staffID, today);

            if (success) {
                response.sendRedirect("adminViewDonations.jsp?msg=rejected");
            } else {
                response.sendRedirect("adminViewDonations.jsp?error=reject_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("adminViewDonations.jsp?error=exception");
        }
    }
}
