<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareShare - Student Distribution Log</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="navbar">
        <div>CareShare Portal</div>
        <div>
            <a href="ViewFoodItemServlet?role=admin">Food Items</a>
            <a href="ViewBookingServlet?action=adminView">Bookings</a>
            <a href="sendNotification.jsp">Send Notification</a>
            <a href="NotificationCategoryServlet">Notification Categories</a>
        </div>
    </div>
    <div class="container">
        <div class="card">
            <h2>Active Student Food Collection Logs</h2>
            <table style="margin-top: 20px;">
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Beneficiary Student</th>
                        <th>Food Item Allotted</th>
                        <th>Distribution Date</th>
                        <th>Distribution Time</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="b" items="${allBookings}">
                        <tr>
                            <td>${b.bookingId}</td>
                            <td>${b.studentName}</td>
                            <td>${b.foodItemName}</td>
                            <td>${b.bookingDate}</td>
                            <td>${b.bookingTime}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
