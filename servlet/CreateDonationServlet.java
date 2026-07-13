package com.careshare.servlet;

import com.careshare.bean.DonationBean;
import com.careshare.dao.DonationDAO;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class CreateDonationServlet extends HttpServlet {

    private final DonationDAO donationDAO = new DonationDAO();
    
    /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateDonationServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateDonationServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }*/

    /*@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }*/

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("donorID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String donorID = (String) session.getAttribute("donorID");

        try {
            String foodItemID = request.getParameter("foodItemID");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String expiryDateStr = request.getParameter("expiryDate");
            String remarks = request.getParameter("remarks");

            DonationBean donation = new DonationBean();
            donation.setDonorID(donorID);
            donation.setFoodItemID(foodItemID);
            donation.setQuantity(quantity);
            donation.setRemarks(remarks);
            if (expiryDateStr != null && !expiryDateStr.trim().isEmpty()) {
                donation.setExpiryDate(java.sql.Date.valueOf(expiryDateStr));
            }

            int newID = donationDAO.createDonation(donation);

            if (newID != -1) {
                request.setAttribute("message", "Donation submitted successfully. It is now pending admin review.");
            } else {
                request.setAttribute("error", "Failed to submit donation. Please try again.");
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid input: " + e.getMessage());
        }

        RequestDispatcher rd = request.getRequestDispatcher("makeDonation.jsp");
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
