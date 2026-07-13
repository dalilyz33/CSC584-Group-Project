<%@page import="java.util.ArrayList"%>
<%@page import="com.careshare.bean.VoucherRedemptionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("studentId") == null) {
        response.sendRedirect(
            request.getContextPath()
            + "/login.jsp"
        );
        return;
    }

    ArrayList<VoucherRedemptionBean> redemptionList =
        (ArrayList<VoucherRedemptionBean>)
        request.getAttribute("redemptionList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Redemption History | CareShare</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/member2.css">
</head>

<body>

<div class="navbar">
    <h2 style="color:white;">CareShare Student</h2>

    <div>
        <a href="<%= request.getContextPath() %>/ViewVoucherServlet">
            Back to My Vouchers
        </a>
    </div>
</div>

<div class="container">

    <div class="card">

        <h1>Voucher Redemption History</h1>

        <table>

            <tr>
                <th>Redemption ID</th>
                <th>Voucher ID</th>
                <th>Redemption Date</th>
            </tr>

            <%
                if (redemptionList != null
                        && !redemptionList.isEmpty()) {

                    for (VoucherRedemptionBean item
                            : redemptionList) {
            %>

            <tr>
                <td><%= item.getRedemptionId() %></td>
                <td><%= item.getVoucherId() %></td>
                <td><%= item.getRedemptionDate() %></td>
            </tr>

            <%
                    }
                } else {
            %>

            <tr>
                <td colspan="3">
                    No redemption history found.
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