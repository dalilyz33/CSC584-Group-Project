<%@page import="java.util.ArrayList"%>
<%@page import="com.careshare.bean.AssistanceApplicationBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("studentId") == null) {
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
    <title>My Applications | CareShare</title>
    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>

<div class="navbar">

    <h2 style="color:white;">
        CareShare Student
    </h2>

    <div>
        <a href="<%= request.getContextPath() %>/studentDashboard.jsp">
            Dashboard
        </a>

        <a href="<%= request.getContextPath() %>/studentAssistanceApplication.jsp">
            Apply Assistance
        </a>

        <a href="<%= request.getContextPath() %>/ViewVoucherServlet">
            My Vouchers
        </a>

        <a href="<%= request.getContextPath() %>/LogoutServlet">
            Logout
        </a>
    </div>

</div>

<div class="container">

    <div class="card">

        <h1>My Assistance Applications</h1>

        <%
            String successMessage =
                    (String) session.getAttribute("successMessage");

            if (successMessage != null) {
        %>
            <div class="message"><%= successMessage %></div>
        <%
                session.removeAttribute("successMessage");
            }
        %>

        <table>

            <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Date</th>
                <th>Document</th>
                <th>Status</th>
            </tr>

            <%
                if (applicationList != null
                        && !applicationList.isEmpty()) {

                    for (AssistanceApplicationBean item
                            : applicationList) {
            %>

            <tr>
                <td>
                    <%= item.getApplicationId() %>
                </td>

                <td>
                    <%= item.getApplicationType() %>
                </td>

                <td>
                    <%= item.getApplicationDate() %>
                </td>

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

                <td>
                    <%= item.getApplicationStatus() %>
                </td>
            </tr>

            <%
                    }

                } else {
            %>

            <tr>
                <td colspan="5">
                    No application record found.
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
