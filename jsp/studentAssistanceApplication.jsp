<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("studentId") == null) {
        response.sendRedirect(
            request.getContextPath()
            + "/login.jsp"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Apply Assistance | CareShare</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>

<div class="navbar">
    <h2 style="color:white;">CareShare Student</h2>

    <div>
        <a href="<%= request.getContextPath() %>/studentDashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/ViewApplicationServlet">My Applications</a>
        <a href="<%= request.getContextPath() %>/ViewVoucherServlet">My Vouchers</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>
    </div>
</div>

<div class="container">

    <div class="card">

        <h1>Assistance Application</h1>

        <p>
            Apply for food aid or social voucher assistance.
        </p>

        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="error">
                <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/ApplyAssistanceServlet"
                method="post"
                enctype="multipart/form-data">

              <label>Assistance Type</label>

              <select name="applicationType" required>
                  <option value="">-- Select Assistance --</option>
                  <option value="Food Aid">Food Aid</option>
                  <option value="Voucher">Social Voucher</option>
              </select>

              <label>Application Date</label>

              <input type="date"
                     name="applicationDate"
                     required>

              <label>Supporting Document</label>

              <input type="file"
                     name="supportingDocument"
                     accept=".pdf,.jpg,.jpeg,.png"
                     required>

              <small>
                  Accepted formats: PDF, JPG, JPEG and PNG.
                  Maximum file size: 5 MB.
              </small>

              <br><br>

              <button type="submit">
                  Submit Application
              </button>

          </form>

    </div>

</div>

</body>
</html>
