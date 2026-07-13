package com.careshare.servlet;

import com.careshare.dao.AssistanceApplicationDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
        name = "ViewApplicationServlet",
        urlPatterns = {"/ViewApplicationServlet"}
)
public class ViewApplicationServlet extends HttpServlet {

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );
            return;
        }

        String role =
                (String) session.getAttribute("userRole");

        AssistanceApplicationDAO dao =
                new AssistanceApplicationDAO();

        if ("Admin".equalsIgnoreCase(role)) {

            if (session.getAttribute("staffId") == null) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/login.jsp"
                );
                return;
            }

            request.setAttribute(
                    "applicationList",
                    dao.getAllApplications()
            );

            request.getRequestDispatcher(
                    "/adminViewApplications.jsp"
            ).forward(request, response);

        } else if ("Student".equalsIgnoreCase(role)) {

            Integer studentId =
                    (Integer) session.getAttribute("studentId");

            if (studentId == null) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/login.jsp"
                );
                return;
            }

            request.setAttribute(
                    "applicationList",
                    dao.getApplicationsByStudent(studentId)
            );

            request.getRequestDispatcher(
                    "/viewMyApplication.jsp"
            ).forward(request, response);

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
}