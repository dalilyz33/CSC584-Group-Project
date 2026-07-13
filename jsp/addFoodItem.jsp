<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Food Item</title>
    </head>
    <body>
        <header class="page-header">
            <div class="brand">CareShare</div>
            <nav>
                <a href="foodItemManagement.jsp">Food Items</a>
                <a href="ReviewDonationServlet">Review Donations</a>
                <a href="InventoryServlet">Inventory</a>
            </nav>
        </header>

        <div class="container">
            <h1>Add Food Item</h1>

            <div class="card">
                <form action="AddFoodItemServlet" method="post">
                    <div class="field">
                        <label for="itemName">Item Name</label>
                        <input type="text" id="itemName" name="itemName" required>
                    </div>

                    <div class="field">
                        <label for="category">Category</label>
                        <input type="text" id="category" name="category" placeholder="e.g. Grains, Canned Goods, Cooking Essentials" required>
                    </div>

                    <div class="field">
                        <label for="itemType">Food Type</label>
                        <select id="itemType" name="itemType" required>
                            <option value="dry food">Dry food</option>
                            <option value="packaged food">Packaged food</option>
                            <option value="fresh produce">Fresh produce</option>
                            <option value="frozen food">Frozen food</option>
                            <option value="ready to eat food">Ready to eat food</option>
                        </select>
                    </div>

                    <div class="field">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" placeholder="Optional notes about this item"></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Save Food Item</button>
                    <a href="foodItemManagement.jsp" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>
    </body>
</html>
