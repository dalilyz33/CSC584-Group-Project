package com.careshare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.careshare.bean.InventoryBean;
import com.careshare.dao.InventoryDAO;

@WebServlet(name = "ExpiredFoodServlet", urlPatterns = {"/ExpiredFoodServlet"})
public class ExpiredFoodServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            InventoryDAO inventoryDAO = new InventoryDAO();
            
            // Fetches all inventory items whose status is 'Expired' or whose date has passed
            List<InventoryBean> expiredItems = inventoryDAO.getExpiredInventory();
            
            // Store the list in request scope to send it to the JSP
            request.setAttribute("expiredFoodList", expiredItems);
            
            // Forward the request back to the user interface page
            request.getRequestDispatcher("expiredFoodList.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("inventoryList.jsp?error=fetch_expired_failed");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

}
