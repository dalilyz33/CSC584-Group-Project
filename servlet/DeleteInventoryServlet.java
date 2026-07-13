package com.careshare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.careshare.dao.InventoryDAO;

@WebServlet(name = "DeleteInventoryServlet", urlPatterns = {"/DeleteInventoryServlet"})
public class DeleteInventoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String inventoryID = request.getParameter("inventoryID");

            InventoryDAO inventoryDAO = new InventoryDAO();
            boolean success = inventoryDAO.deleteInventory(inventoryID);

            if (success) {
                response.sendRedirect("inventoryList.jsp?msg=deleted");
            } else {
                response.sendRedirect("inventoryList.jsp?error=delete_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("inventoryList.jsp?error=exception");
        }
    }

}
