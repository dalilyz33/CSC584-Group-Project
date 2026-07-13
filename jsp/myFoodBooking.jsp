<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareShare - My Orders</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="navbar">
        <div>CareShare Student</div>
        <div>
            <a href="ViewFoodItemServlet?role=student">Browse Food</a>
            <a href="ViewBookingServlet?action=myBookings">My Reservations</a>
            <a href="ViewNotificationServlet">Notifications</a>
        </div>
    </div>
    <div class="container">
        <div class="card">
            <h2>Your Booked Food Collections</h2>
            <table style="margin-top: 15px;">
                <thead>
                    <tr>
                        <th>Reservation Ref ID</th>
                        <th>Food Item</th>
                        <th>Scheduled Date</th>
                        <th>Scheduled Time</th>
                        <th>Management</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="b" items="${myBookings}">
                        <tr>
                            <td>#${b.bookingId}</td>
                            <td><strong>${b.foodItemName}</strong></td>
                            <td>${b.bookingDate}</td>
                            <td>${b.bookingTime}</td>
                            <td>
                                <a href="CancelBookingServlet?id=${b.bookingId}" class="button reject" onclick="return confirm('Cancel this collection?')">Revoke</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
