<%@page import="com.careshare.bean.VoucherBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("studentId") == null) {
        response.sendRedirect(
            request.getContextPath()
            + "/login.jsp"
        );
        return;
    }

    String voucherId =
        request.getParameter("voucherId");

    String voucherValue =
        request.getParameter("voucherValue");

    String expiryDate =
        request.getParameter("expiryDate");

    VoucherBean voucher =
        (VoucherBean) request.getAttribute("voucher");

    if (voucher != null) {
        voucherId = String.valueOf(voucher.getVoucherId());
        voucherValue = String.valueOf(voucher.getVoucherValue());
        expiryDate = String.valueOf(voucher.getVoucherExpiryDate());
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Redeem Voucher | CareShare</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>

<div class="navbar">
    <h2 style="color:white;">CareShare Student</h2>
</div>

<div class="container">

    <div class="card">

        <h1>Confirm Voucher Redemption</h1>

        <% if (request.getAttribute("errorMessage") != null) { %>

        <div class="error">
            <%= request.getAttribute("errorMessage") %>
        </div>

        <% } %>

        <p>
            <strong>Voucher ID:</strong>
            <%= voucherId %>
        </p>

        <p>
            <strong>Value:</strong>
            RM <%= voucherValue %>
        </p>

        <p>
            <strong>Expiry Date:</strong>
            <%= expiryDate %>
        </p>

        <form action="<%= request.getContextPath() %>/RedeemVoucherServlet"
      method="post">

            <input type="hidden"
                   name="voucherId"
                   value="<%= voucherId %>">

            <button type="submit">
                Confirm Redemption
            </button>

            <a class="button"
               href="<%= request.getContextPath() %>/ViewVoucherServlet">

                Cancel

            </a>

        </form>

    </div>

</div>

</body>
</html>
