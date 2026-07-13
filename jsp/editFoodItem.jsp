<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareShare - Edit Food Item</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <div class="card" style="max-width: 600px; margin: 40px auto;">
            <h2>Modify Food Entry</h2>
            <form action="UpdateFoodItemServlet" method="POST">
                <input type="hidden" name="foodItemId" value="${foodItem.foodItemId}">

                <label>Food Item Name</label>
                <input type="text" name="foodItemName" value="${foodItem.foodItemName}" required>

                <label>Food Type</label>
                <input type="text" name="foodItemType" value="${foodItem.foodItemType}" required>

                <label>Description</label>
                <textarea name="foodItemDescription" rows="4" required>${foodItem.foodItemDescription}</textarea>

                <label>Notification Category ID</label>
                <input type="number" name="categoryId" value="${foodItem.categoryId}" required>

                <button type="submit" class="approve">Apply Changes</button>
                <a href="ViewFoodItemServlet?role=admin" class="button">Cancel</a>
            </form>
        </div>
    </div>
</body>
</html>
