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

import com.careshare.bean.FoodItemBean;
import com.careshare.dao.FoodItemDAO;

@WebServlet(name = "UpdateFoodItemServlet", urlPatterns = {"/UpdateFoodItemServlet"})
public class UpdateFoodItemServlet extends HttpServlet {

    private final FoodItemDAO foodItemDAO = new FoodItemDAO();
    
    /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code.
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateFoodItemServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateFoodItemServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String foodItemID = request.getParameter("foodItemID");
        FoodItemBean item = foodItemDAO.getFoodItemById(foodItemID);
        request.setAttribute("foodItem", item);
        RequestDispatcher rd = request.getRequestDispatcher("editFoodItem.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        FoodItemBean item = new FoodItemBean();
        item.setFoodItemID(request.getParameter("foodItemID"));
        item.setFoodItemName(request.getParameter("foodItemName"));
        item.setCategory(request.getParameter("category"));
        item.setDescription(request.getParameter("description"));

        boolean updated = foodItemDAO.updateFoodItem(item);
        
        HttpSession session = request.getSession();
        
        if (updated) {
            session.setAttribute("message", "Food item updated successfully.");
            session.setAttribute("messageType", "success");
        } else {
            session.setAttribute("message", "Could not update this item — it may already be used in a donation or inventory record.");
            session.setAttribute("messageType", "error");
        }
        response.sendRedirect("foodItemManagement.jsp");
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
