package com.careshare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.careshare.bean.FoodItemBean;
import com.careshare.dao.FoodItemDAO;

@WebServlet(name = "AddFoodItemServlet", urlPatterns = {"/AddFoodItemServlet"})
public class AddFoodItemServlet extends HttpServlet {

    private final FoodItemDAO foodItemDAO = new FoodItemDAO();
    
    /*
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddFoodItemServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddFoodItemServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.sendRedirect("addFoodItem.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        FoodItemBean item = new FoodItemBean();
        item.setFoodItemName(request.getParameter("foodItemName"));
        item.setCategory(request.getParameter("category"));
        item.setDescription(request.getParameter("description"));

        int newId = foodItemDAO.addFoodItem(item);
        
        HttpSession session = request.getSession();
        
        if (newId != -1) {
            session.setAttribute("message", "Food item added successfully.");
            session.setAttribute("messageType", "success");
        } else {
            session.setAttribute("message", "Failed to add food item.");
            session.setAttribute("messageType", "error");
        }
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
