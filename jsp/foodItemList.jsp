<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareShare - Manage Food Items</title>
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
            <h1>Food Item Catalog Management</h1>
            <a href="addFoodItem.jsp" class="button" style="margin-bottom: 20px;">+ Add New Food Item</a>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Description</th>
                        <th>Category ID</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${foodItems}">
                        <tr>
                            <td>${item.foodItemId}</td>
                            <td><strong>${item.foodItemName}</strong></td>
                            <td>${item.foodItemType}</td>
                            <td>${item.foodItemDescription}</td>
                            <td>${item.categoryId}</td>
                            <td>
                                <a href="ViewFoodItemServlet?action=edit&id=${item.foodItemId}" class="button approve">Edit</a>
                                <a href="DeleteFoodItemServlet?id=${item.foodItemId}" class="button reject" onclick="return confirm('Delete item?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
