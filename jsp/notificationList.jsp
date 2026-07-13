<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareShare - Updates Inbox</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="navbar">
        <div>CareShare Network</div>
        <div>
            <a href="ViewFoodItemServlet?role=student">Browse Food</a>
            <a href="ViewBookingServlet?action=myBookings">My Reservations</a>
            <a href="ViewNotificationServlet">Notifications</a>
        </div>
    </div>
    <div class="container">
        <h2>Your Notifications Inbox</h2>
        <div style="margin-top: 20px;">
            <c:forEach var="n" items="${notifications}">
                <div class="card" style="margin-bottom: 15px; border-left: 5px solid #12355b;">
                    <span style="font-size: 11px; font-weight: bold; text-transform: uppercase; color:#475569;">[${n.categoryName}]</span>
                    <p style="margin: 8px 0;">${n.notificationMessage}</p>
                    <span style="font-size: 12px; color:#94a3b8;">Sent: ${n.notificationDateSent}</span>
                    <span style="float: right;" class="message">${n.notificationStatus}</span>
                </div>
            </c:forEach>
            <c:if test="${empty notifications}">
                <div class="card"><p>Your inbox is currently clear.</p></div>
            </c:if>
        </div>
    </div>
</body>
</html>
