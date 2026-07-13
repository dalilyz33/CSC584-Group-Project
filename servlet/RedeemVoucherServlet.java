package com.careshare.servlet;

import com.careshare.bean.VoucherBean;
import com.careshare.bean.VoucherRedemptionBean;
import com.careshare.dao.VoucherDAO;
import com.careshare.dao.VoucherRedemptionDAO;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
        name = "RedeemVoucherServlet",
        urlPatterns = {"/RedeemVoucherServlet"}
)
public class RedeemVoucherServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("studentId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );
            return;
        }

        String voucherIdText =
                request.getParameter("voucherId");

        if (voucherIdText == null
                || voucherIdText.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/ViewVoucherServlet"
            );
            return;
        }

        try {
            int studentId =
                    (Integer) session.getAttribute("studentId");

            int voucherId =
                    Integer.parseInt(voucherIdText);

            VoucherDAO voucherDAO =
                    new VoucherDAO();

            VoucherBean voucher =
                    voucherDAO.getVoucherById(voucherId);

            Date today =
                    new Date(System.currentTimeMillis());

            if (voucher == null
                    || voucher.getStudentId() != studentId
                    || !"Active".equalsIgnoreCase(
                            voucher.getVoucherStatus())
                    || voucher.getVoucherExpiryDate() == null
                    || voucher.getVoucherExpiryDate().before(today)) {

                request.setAttribute(
                        "errorMessage",
                        "Voucher is invalid, expired or already used."
                );

                request.setAttribute("voucher", voucher);

                request.getRequestDispatcher(
                        "/redeemVoucher.jsp"
                ).forward(request, response);

                return;
            }

            VoucherRedemptionBean redemption =
                    new VoucherRedemptionBean();

            redemption.setStudentId(studentId);
            redemption.setVoucherId(voucherId);
            redemption.setRedemptionDate(today);

            VoucherRedemptionDAO redemptionDAO =
                    new VoucherRedemptionDAO();

            String result =
                    redemptionDAO.addRedemption(redemption);

            if ("SUCCESS".equals(result)) {

                String updateResult =
                        voucherDAO.markVoucherUsed(voucherId);

                if ("SUCCESS".equals(updateResult)) {

                    response.sendRedirect(
                            request.getContextPath()
                            + "/VoucherRedemptionServlet"
                    );

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Redemption was saved, but voucher status could not be updated."
                    );

                    request.getRequestDispatcher(
                            "/viewMyVoucher.jsp"
                    ).forward(request, response);
                }

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ViewVoucherServlet"
                );
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/ViewVoucherServlet"
            );
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/ViewVoucherServlet"
        );
    }
}
