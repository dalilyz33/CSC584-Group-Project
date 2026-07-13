<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard - CareShare</title>
</head>
<body>

<%
    HttpSession userSession = request.getSession(false);
    if (userSession == null
            || userSession.getAttribute("userRole") == null
            || !"Student".equals(userSession.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    String fullName = (String) userSession.getAttribute("userFullName");
    String studentId = (String) userSession.getAttribute("studentId");
%>

    <h1>Student Dashboard</h1>
    <p>Welcome, <%= fullName %> (Student ID: <%= studentId %>)</p>

    <h2>My Assistance Applications</h2>
    <p>[Placeholder - populated once Member 2's AssistanceApplicationDao exists]</p>

    <h2>My Vouchers</h2>
    <p>[Placeholder - populated once Member 2's VoucherDao exists]</p>

    <h2>My Food Bookings</h2>
    <p>[Placeholder - populated once Member 4's FoodCollectionBookingDao exists]</p>

    <a href="LogoutServlet">Logout</a>

</body>
</html>
