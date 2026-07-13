package com.careshare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

import com.careshare.bean.InventoryBean;
import com.careshare.dao.InventoryDAO;

@WebServlet(name = "UpdateInventoryServlet", urlPatterns = {"/UpdateInventoryServlet"})
public class UpdateInventoryServlet extends HttpServlet {

    private final InventoryDAO inventoryDAO = new InventoryDAO();
    
    /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code.
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateInventoryServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateInventoryServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        InventoryBean inv = new InventoryBean();
        inv.setInventoryID(request.getParameter("inventoryID"));
        inv.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        inv.setFoodStatus(request.getParameter("foodStatus"));

        String expiryDateStr = request.getParameter("expiryDate");
        if (expiryDateStr != null && !expiryDateStr.trim().isEmpty()) {
            inv.setExpiryDate(Date.valueOf(expiryDateStr));
        }

        boolean updated = inventoryDAO.updateInventory(inv);

        HttpSession session = request.getSession();
        if (updated) {
            session.setAttribute("message", "Food item deleted successfully.");
            session.setAttribute("messageType", "success");
        } else {
            session.setAttribute("message", "Could not delete this item — it may already be used in a donation or inventory record.");
            session.setAttribute("messageType", "error");
        }

        response.sendRedirect("InventoryServlet");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
