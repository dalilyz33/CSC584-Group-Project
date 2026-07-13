<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("staffId") == null) {
        response.sendRedirect(
            request.getContextPath()
            + "/login.jsp"
        );
        return;
    }

    String applicationId =
        request.getParameter("applicationId");

    String studentId =
        request.getParameter("studentId");

    String applicationType =
        request.getParameter("applicationType");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Review Application | CareShare</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>

<div class="navbar">
    <h2 style="color:white;">CareShare Admin</h2>

    <div>
        <a href="<%= request.getContextPath() %>/ViewApplicationServlet">
            Back to Applications
        </a>
    </div>
</div>

<div class="container">

    <div class="card">

        <h1>Review Application</h1>

        <p>
            <strong>Application ID:</strong>
            <%= applicationId %>
        </p>

        <p>
            <strong>Student ID:</strong>
            <%= studentId %>
        </p>

        <p>
            <strong>Application Type:</strong>
            <%= applicationType %>
        </p>

        <form action="<%= request.getContextPath() %>/ReviewApplicationServlet"
      method="post">

            <input type="hidden"
                   name="applicationId"
                   value="<%= applicationId %>">

            <button class="approve"
                    type="submit"
                    name="status"
                    value="Approved">

                Approve

            </button>

            <button class="reject"
                    type="submit"
                    name="status"
                    value="Rejected">

                Reject

            </button>

        </form>

    </div>

</div>

</body>
</html>
