package com.careshare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

import com.careshare.bean.DonationBean;
import com.careshare.dao.DonationDAO;

@WebServlet(name = "AddDonationServlet", urlPatterns = {"/AddDonationServlet"})
public class AddDonationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String donorID = request.getParameter("donorID");
            String foodItemID = request.getParameter("foodItemID");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Timestamp donationDate = Timestamp.valueOf(request.getParameter("donationDate"));

            DonationBean donation = new DonationBean();
            donation.setDonorID(donorID);
            donation.setFoodItemID(foodItemID);
            donation.setQuantity(quantity);
            donation.setDonationDate(donationDate);
            donation.setStatus("Pending"); // Default initial status

            DonationDAO donationDAO = new DonationDAO();
            int generatedID = donationDAO.createDonation(donation);
            boolean success = (generatedID > 0);

            if (success) {
                response.sendRedirect("donorDonationHistory.jsp?msg=success");
            } else {
                response.sendRedirect("donorDonationForm.jsp?error=failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("donorDonationForm.jsp?error=invalid_input");
        }
    }

}
