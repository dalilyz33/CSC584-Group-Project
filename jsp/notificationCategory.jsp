<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareShare - Notification Schemes</title>
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
    <div class="container" style="display: flex; gap: 30px;">
        <div class="card" style="flex: 1;">
            <h2>Existing Alert Categories</h2>
            <table style="margin-top: 15px;">
                <thead>
                    <tr>
                        <th>Category ID</th>
                        <th>System Label Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cat" items="${categories}">
                        <tr>
                            <td>${cat.categoryId}</td>
                            <td><strong>${cat.categoryName}</strong></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <div class="card" style="flex: 1; max-height: 300px;">
            <h2>Add Alert Category</h2>
            <form action="NotificationCategoryServlet" method="POST" style="margin-top: 15px;">
                <label>Category Label Name</label>
                <input type="text" name="categoryName" placeholder="e.g., Food Alert, System Outage" required>
                
                <button type="submit">Commit New Category</button>
            </form>
        </div>
    </div>
</body>
</html>
