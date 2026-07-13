<%@page import="java.util.ArrayList"%>
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

    ArrayList<VoucherBean> voucherList =
        (ArrayList<VoucherBean>)
        request.getAttribute("voucherList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Vouchers | CareShare</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/member2.css">
</head>

<body>

<div class="navbar">
    <h2 style="color:white;">CareShare Student</h2>

    <div>
        <a href="<%= request.getContextPath() %>/studentDashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/ViewApplicationServlet">Applications</a>
        <a href="<%= request.getContextPath() %>/VoucherRedemptionServlet">
            Redemption History
        </a>
        <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>
    </div>
</div>

<div class="container">

    <div class="card">

        <h1>My Social Vouchers</h1>

        <table>

            <tr>
                <th>Voucher ID</th>
                <th>Quantity</th>
                <th>Value</th>
                <th>Assigned Date</th>
                <th>Expiry Date</th>
                <th>Status</th>
                <th>Action</th>
            </tr>

            <%
                if (voucherList != null
                        && !voucherList.isEmpty()) {

                    for (VoucherBean voucher : voucherList) {
            %>

            <tr>

                <td><%= voucher.getVoucherId() %></td>

                <td><%= voucher.getVoucherQuantity() %></td>

                <td>
                    RM <%= voucher.getVoucherValue() %>
                </td>

                <td>
                    <%= voucher.getVoucherAssignedDate() %>
                </td>

                <td>
                    <%= voucher.getVoucherExpiryDate() %>
                </td>

                <td>
                    <%= voucher.getVoucherStatus() %>
                </td>

                <td>

                    <% if ("Active".equals(
                            voucher.getVoucherStatus())) { %>

                    <a class="button"
                       href="<%= request.getContextPath() %>/redeemVoucher.jsp?voucherId=<%= voucher.getVoucherId() %>&voucherValue=<%= voucher.getVoucherValue() %>&expiryDate=<%= voucher.getVoucherExpiryDate() %>">

                        Redeem

                    </a>

                    <% } else { %>

                    Not Available

                    <% } %>

                </td>

            </tr>

            <%
                    }
                } else {
            %>

            <tr>
                <td colspan="7">
                    No voucher has been assigned.
                </td>
            </tr>

            <%
                }
            %>

        </table>

    </div>

</div>

</body>
</html>