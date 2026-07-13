package com.careshare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.careshare.dao.FoodItemDAO;

@WebServlet(name = "DeleteFoodItemServlet", urlPatterns = {"/DeleteFoodItemServlet"})
public class DeleteFoodItemServlet extends HttpServlet {

    private final FoodItemDAO foodItemDAO = new FoodItemDAO();
    
    /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code.
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeleteFoodItemServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteFoodItemServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String foodItemID = request.getParameter("foodItemID");
        boolean deleted = foodItemDAO.deleteFoodItem(foodItemID);

        /*HttpSessionMessage.set(request, deleted,
                deleted ? "Food item deleted successfully."
                        : "Could not delete this item — it may already be used in a donation or inventory record.");*/

        HttpSession session = request.getSession();
        
        if (deleted) {
            session.setAttribute("message", "Food item deleted successfully.");
            session.setAttribute("messageType", "success");
        } else {
            session.setAttribute("message", "Could not delete this item — it may already be used in a donation or inventory record.");
            session.setAttribute("messageType", "error");
        }
        response.sendRedirect("foodItemManagement.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
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
