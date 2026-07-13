package com.careshare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.util.List;

import com.careshare.bean.DonationBean;
import com.careshare.dao.DonationDAO;
import com.careshare.dao.InventoryDAO;

@WebServlet(name = "ReviewDonationServlet", urlPatterns = {"/ReviewDonationServlet"})
public class ReviewDonationServlet extends HttpServlet {

    private final DonationDAO donationDAO = new DonationDAO();
    private final InventoryDAO inventoryDAO = new InventoryDAO();
    
    /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReviewDonationServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewDonationServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        List<DonationBean> pending = donationDAO.getPendingDonations();
        request.setAttribute("donations", pending);
        request.getRequestDispatcher("reviewDonation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staffID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String staffID = (String) session.getAttribute("staffID");

        String donationID = request.getParameter("donationId");
        String decision = request.getParameter("decision"); // "APPROVED" or "REJECTED"
        String remarks = request.getParameter("remarks");

        boolean updated = donationDAO.updateDonationStatus(donationID, decision, staffID);

        if (updated && "APPROVED".equals(decision)) {
            DonationBean donation = donationDAO.getDonationById(donationID);
            if (donation != null) {
                inventoryDAO.addInventoryFromDonation(donation);
            }
        }

        request.setAttribute("message", updated
                ? "Donation #" + donationID + " marked as " + decision + "."
                : "Failed to update donation #" + donationID + ".");

        List<DonationBean> pending = donationDAO.getPendingDonations();
        request.setAttribute("donations", pending);

        RequestDispatcher rd = request.getRequestDispatcher("reviewDonation.jsp");
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
