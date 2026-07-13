package com.careshare.servlet;

import com.careshare.bean.VoucherBean;
import com.careshare.dao.VoucherDAO;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
        name = "AssignVoucherServlet",
        urlPatterns = {"/AssignVoucherServlet"}
)
public class AssignVoucherServlet extends HttpServlet {

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

        String studentIdText =
                request.getParameter("studentId");

        String categoryIdText =
                request.getParameter("categoryId");

        String quantityText =
                request.getParameter("voucherQuantity");

        String valueText =
                request.getParameter("voucherValue");

        String assignedDateText =
                request.getParameter("assignedDate");

        String expiryDateText =
                request.getParameter("expiryDate");

        if (studentIdText == null
                || studentIdText.trim().isEmpty()
                || categoryIdText == null
                || categoryIdText.trim().isEmpty()
                || quantityText == null
                || quantityText.trim().isEmpty()
                || valueText == null
                || valueText.trim().isEmpty()
                || assignedDateText == null
                || assignedDateText.trim().isEmpty()
                || expiryDateText == null
                || expiryDateText.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "Please complete all voucher fields."
            );

            request.getRequestDispatcher(
                    "/assignVoucher.jsp"
            ).forward(request, response);

            return;
        }

        try {
            int staffId =
                    (Integer) session.getAttribute("staffId");

            int studentId =
                    Integer.parseInt(studentIdText);

            int categoryId =
                    Integer.parseInt(categoryIdText);

            int quantity =
                    Integer.parseInt(quantityText);

            double value =
                    Double.parseDouble(valueText);

            Date assignedDate =
                    Date.valueOf(assignedDateText);

            Date expiryDate =
                    Date.valueOf(expiryDateText);

            if (quantity <= 0 || value <= 0) {

                request.setAttribute(
                        "errorMessage",
                        "Quantity and value must be more than zero."
                );

                request.getRequestDispatcher(
                        "/assignVoucher.jsp"
                ).forward(request, response);

                return;
            }

            if (expiryDate.before(assignedDate)) {

                request.setAttribute(
                        "errorMessage",
                        "Expiry date must be after the assigned date."
                );

                request.getRequestDispatcher(
                        "/assignVoucher.jsp"
                ).forward(request, response);

                return;
            }

            VoucherBean voucher =
                    new VoucherBean();

            voucher.setStudentId(studentId);
            voucher.setCategoryId(categoryId);
            voucher.setVoucherAssignedBy(staffId);
            voucher.setVoucherQuantity(quantity);
            voucher.setVoucherValue(value);
            voucher.setVoucherStatus("Active");
            voucher.setVoucherAssignedDate(assignedDate);
            voucher.setVoucherExpiryDate(expiryDate);

            VoucherDAO dao =
                    new VoucherDAO();

            String result =
                    dao.assignVoucher(voucher);

            if ("SUCCESS".equals(result)) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ViewApplicationServlet"
                );

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Voucher could not be assigned. Check the database IDs."
                );

                request.getRequestDispatcher(
                        "/assignVoucher.jsp"
                ).forward(request, response);
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Student ID, category, quantity and value must be valid numbers."
            );

            request.getRequestDispatcher(
                    "/assignVoucher.jsp"
            ).forward(request, response);

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "errorMessage",
                    "Please enter valid assigned and expiry dates."
            );

            request.getRequestDispatcher(
                    "/assignVoucher.jsp"
            ).forward(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/ViewApplicationServlet"
        );
    }
}
