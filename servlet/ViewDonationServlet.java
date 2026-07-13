package com.careshare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import com.careshare.bean.DonationBean;
import com.careshare.dao.DonationDAO;

@WebServlet(name = "ViewDonationServlet", urlPatterns = {"/ViewDonationServlet"})
public class ViewDonationServlet extends HttpServlet {

    private final DonationDAO donationDAO = new DonationDAO();
    
    /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewDonationServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewDonationServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("donorID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String donorID = (String) session.getAttribute("donorId");

        List<DonationBean> donations = donationDAO.getDonationsByDonor(donorID);
        request.setAttribute("donations", donations);

        RequestDispatcher rd = request.getRequestDispatcher("myDonation.jsp");
        rd.forward(request, response);
    }

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
