package com.careshare.servlet;

import com.careshare.dao.VoucherDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(
        name = "ViewVoucherServlet",
        urlPatterns = {"/ViewVoucherServlet"}
)
public class ViewVoucherServlet
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

        VoucherDAO dao =
                new VoucherDAO();

        request.setAttribute(
                "voucherList",
                dao.getVouchersByStudent(studentId)
        );

        request.getRequestDispatcher(
                "/viewMyVoucher.jsp"
        ).forward(request, response);
    }
}