package com.careshare.servlet;

import com.careshare.bean.AssistanceApplicationBean;
import com.careshare.dao.AssistanceApplicationDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
        name = "ReviewApplicationServlet",
        urlPatterns = {"/ReviewApplicationServlet"}
)
public class ReviewApplicationServlet extends HttpServlet {

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("staffId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );
            return;
        }

        String applicationIdText =
                request.getParameter("applicationId");

        String status =
                request.getParameter("status");

        if (applicationIdText == null
                || applicationIdText.trim().isEmpty()
                || status == null
                || (!"Approved".equalsIgnoreCase(status)
                && !"Rejected".equalsIgnoreCase(status))) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/ViewApplicationServlet"
            );
            return;
        }

        try {
            int staffId =
                    (Integer) session.getAttribute("staffId");

            int applicationId =
                    Integer.parseInt(applicationIdText);

            AssistanceApplicationDAO dao =
                    new AssistanceApplicationDAO();

            AssistanceApplicationBean application =
                    dao.getApplicationById(applicationId);

            if (application == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ViewApplicationServlet"
                );
                return;
            }

            String result =
                    dao.reviewApplication(
                            applicationId,
                            staffId,
                            status
                    );

            if ("SUCCESS".equals(result)
                    && "Approved".equalsIgnoreCase(status)
                    && "Voucher".equalsIgnoreCase(
                            application.getApplicationType())) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/assignVoucher.jsp"
                        + "?applicationId="
                        + applicationId
                        + "&studentId="
                        + application.getStudentId()
                );

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ViewApplicationServlet"
                );
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/ViewApplicationServlet"
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
}
