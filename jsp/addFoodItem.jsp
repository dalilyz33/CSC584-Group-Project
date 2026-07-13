<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareShare - Add Food Item</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <div class="card" style="max-width: 600px; margin: 40px auto;">
            <h2>Create Food Entry</h2>
            <form action="AddFoodItemServlet" method="POST">
                <label>Food Item Name</label>
                <input type="text" name="foodItemName" required>

                <label>Food Type</label>
                <select name="foodItemType">
                    <option value="Dry Provisions">Dry Provisions</option>
                    <option value="Fresh Goods">Fresh Goods</option>
                    <option value="Canned Goods">Canned Goods</option>
                </select>

                <label>Description</label>
                <textarea name="foodItemDescription" rows="4" required></textarea>

                <label>Notification Category Assignment (Category ID)</label>
                <input type="number" name="categoryId" required>

                <button type="submit">Publish to Catalog</button>
                <a href="ViewFoodItemServlet?role=admin" class="button reject">Back</a>
            </form>
        </div>
    </div>
</body>
</html>
