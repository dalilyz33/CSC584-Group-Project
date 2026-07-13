<%@page import="java.util.ArrayList"%>
<%@page import="com.careshare.bean.AssistanceApplicationBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("staffId") == null) {
        response.sendRedirect(
            request.getContextPath()
            + "/login.jsp"
        );
        return;
    }

    ArrayList<AssistanceApplicationBean> applicationList =
        (ArrayList<AssistanceApplicationBean>)
        request.getAttribute("applicationList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Applications | CareShare Admin</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/member2.css">
</head>

<body>

<div class="navbar">
    <h2 style="color:white;">CareShare Admin</h2>

    <div>
        <a href="<%= request.getContextPath() %>/adminDashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/ViewApplicationServlet">Applications</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>
    </div>
</div>

<div class="container">

    <div class="card">

        <h1>Student Assistance Applications</h1>

        <table>

            <tr>
                <th>ID</th>
                <th>Student ID</th>
                <th>Type</th>
                <th>Date</th>
                <th>Document</th>
                <th>Status</th>
                <th>Action</th>
            </tr>

            <%
                if (applicationList != null) {

                    for (AssistanceApplicationBean item
                            : applicationList) {
            %>

            <tr>

                <td><%= item.getApplicationId() %></td>

                <td><%= item.getStudentId() %></td>

                <td><%= item.getApplicationType() %></td>

                <td><%= item.getApplicationDate() %></td>

                <td>
                    <% if (item.getApplicationSupportingDocument() != null
                            && !item.getApplicationSupportingDocument().isEmpty()) { %>

                        <a href="<%= request.getContextPath() %>/uploads/<%= item.getApplicationSupportingDocument() %>"
                           target="_blank">
                            View Document
                        </a>

                    <% } else { %>

                        No document

                    <% } %>
                </td>

                <td><%= item.getApplicationStatus() %></td>

                <td>

                    <% if ("Pending".equals(
                            item.getApplicationStatus())) { %>

                    <a class="button"
                       href="<%= request.getContextPath() %>/reviewApplication.jsp?applicationId=<%= item.getApplicationId() %>&studentId=<%= item.getStudentId() %>&applicationType=<%= item.getApplicationType() %>">
                        Review
                    </a>

                    <% } else { %>

                    Completed

                    <% } %>

                </td>

            </tr>

            <%
                    }
                }
            %>

        </table>

    </div>

</div>

</body>
</html>