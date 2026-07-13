<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Donor Dashboard - CareShare</title>
</head>
<body>

<%
    HttpSession userSession = request.getSession(false);
    if (userSession == null
            || userSession.getAttribute("userRole") == null
            || !"Donor".equals(userSession.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    String fullName = (String) userSession.getAttribute("userFullName");
    String donorId = (String) userSession.getAttribute("donorId");
%>

    <h1>Donor Dashboard</h1>
    <p>Welcome, <%= fullName %> (Donor ID: <%= donorId %>)</p>

    <h2>My Donation History</h2>
    <p>[Placeholder - populated once Member 3's DonationDao exists]</p>

    <a href="LogoutServlet">Logout</a>

</body>
</html>