package com.careshare.servlet;

import com.careshare.dao.VoucherRedemptionDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(
        name = "VoucherRedemptionServlet",
        urlPatterns = {"/VoucherRedemptionServlet"}
)
public class VoucherRedemptionServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("studentId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );
            return;
        }

        int studentId =
                (Integer) session.getAttribute("studentId");

        VoucherRedemptionDAO dao =
                new VoucherRedemptionDAO();

        request.setAttribute(
                "redemptionList",
                dao.getHistoryByStudent(studentId)
        );

        request.getRequestDispatcher(
                "/voucherRedemptionHistory.jsp"
        ).forward(request, response);
    }
}