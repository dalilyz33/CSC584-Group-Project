/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.careshare.dao.InventoryDAO;
import com.careshare.bean.InventoryBean;

@WebServlet(name = "ApproveDonationServlet", urlPatterns = {"/ApproveDonationServlet"})
public class ApproveDonationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String donationID = request.getParameter("donationID");
            String foodItemID = request.getParameter("foodItemID");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String staffID = request.getParameter("staffID");
            Date expiryDate = Date.valueOf(request.getParameter("expiryDate"));
            Date today = new Date(System.currentTimeMillis());

            DonationDAO donationDAO = new DonationDAO();
            // 1. Update status to Approved, set staff_id and date approved
            boolean donationUpdated = donationDAO.approveDonation(donationID, staffID, today);

            if (donationUpdated) {
                // 2. Generate Inventory item entry
                InventoryBean inventoryItem = new InventoryBean();
                inventoryItem.setStaffID(staffID);
                inventoryItem.setDonationID(donationID);
                inventoryItem.setFoodItemID(foodItemID);
                inventoryItem.setQuantity(quantity);
                inventoryItem.setExpiryDate(expiryDate);
                inventoryItem.setFoodStatus("Available");

                InventoryDAO inventoryDAO = new InventoryDAO();
                boolean inventoryAdded = inventoryDAO.addInventory(inventoryItem);

                if (inventoryAdded) {
                    response.sendRedirect("adminViewDonations.jsp?msg=approved");
                } else {
                    response.sendRedirect("approveDonation.jsp?error=inventory_failed");
                }
            } else {
                response.sendRedirect("adminViewDonations.jsp?error=update_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("adminViewDonations.jsp?error=exception");
        }
    }

}
