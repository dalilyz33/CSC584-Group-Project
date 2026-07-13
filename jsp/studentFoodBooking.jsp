<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareShare - Collection Booking</title>
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
        <h2>Book a Food Item Collection Slot</h2>
        <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); gap: 20px; margin-top:20px;">
            <c:forEach var="item" items="${foodItems}">
                <div class="card">
                    <h3>${item.foodItemName}</h3>
                    <p style="color: #475569;">Type: ${item.foodItemType}</p>
                    <p>${item.foodItemDescription}</p>
                    <hr style="border:0; border-top:1px solid #d9e3ee; margin: 15px 0;">
                    <form action="BookFoodCollectionServlet" method="POST">
                        <input type="hidden" name="foodItemId" value="${item.foodItemId}">
                        <input type="hidden" name="categoryId" value="${item.categoryId}">
                        
                        <label>Collection Date</label>
                        <input type="date" name="bookingDate" required>
                        
                        <label>Collection Time</label>
                        <input type="time" name="bookingTime" required>
                        
                        <button type="submit" style="width:100%;">Confirm Booking Slot</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>

