<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - CareShare</title>
</head>
<body>

<%
    HttpSession userSession = request.getSession(false);
    if (userSession == null
            || userSession.getAttribute("userRole") == null
            || !"Admin".equals(userSession.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    String fullName = (String) userSession.getAttribute("userFullName");
    String staffId = (String) userSession.getAttribute("staffId");
%>

    <h1>Admin Dashboard</h1>
    <p>Welcome, <%= fullName %> (Staff ID: <%= staffId %>)</p>

    <h2>Pending Assistance Applications</h2>
    <p>[Placeholder - Member 2's AssistanceApplicationDao]</p>

    <h2>Pending Donations</h2>
    <p>[Placeholder - Member 3's DonationDao]</p>

    <h2>Inventory Overview</h2>
    <p>[Placeholder - Member 3's InventoryDao]</p>

    <h2>Food Collection Bookings</h2>
    <p>[Placeholder - Member 4's FoodCollectionBookingDao]</p>

    <a href="LogoutServlet">Logout</a>

</body>
</html>