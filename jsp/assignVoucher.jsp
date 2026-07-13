<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("staffId") == null) {
        response.sendRedirect(
            request.getContextPath()
            + "/login.jsp"
        );
        return;
    }

    String studentId =
        request.getParameter("studentId");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assign Voucher | CareShare</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/member2.css">
</head>

<body>

<div class="navbar">
    <h2 style="color:white;">CareShare Admin</h2>
</div>

<div class="container">

    <div class="card">

        <h1>Assign Social Voucher</h1>

        <% if (request.getAttribute("errorMessage") != null) { %>

        <div class="error">
            <%= request.getAttribute("errorMessage") %>
        </div>

        <% } %>

        <form action="<%= request.getContextPath() %>/AssignVoucherServlet"
      method="post">

            <label>Student ID</label>

            <input type="number"
                   name="studentId"
                   value="<%= studentId %>"
                   readonly>

            <label>Voucher Category ID</label>

            <input type="number"
                   name="categoryId"
                   value="1"
                   required>

            <label>Voucher Quantity</label>

            <input type="number"
                   name="voucherQuantity"
                   value="1"
                   min="1"
                   required>

            <label>Voucher Value (RM)</label>

            <input type="number"
                   name="voucherValue"
                   step="0.01"
                   min="1"
                   required>

            <label>Assigned Date</label>

            <input type="date"
                   name="assignedDate"
                   required>

            <label>Expiry Date</label>

            <input type="date"
                   name="expiryDate"
                   required>

            <button type="submit">
                Assign Voucher
            </button>

        </form>

    </div>

</div>

</body>
</html>