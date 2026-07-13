<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.careshare.bean.FoodItemBean" %>
<%@ page import="com.careshare.dao.FoodItemDAO" %>
<%@ page import="java.util.List" %>
<%
    FoodItemBean item = (FoodItemBean) request.getAttribute("foodItem");
    String[] foodItemTypes = {"dry food", "packaged food", "fresh produce", "frozen food", "ready to eat food"};
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Food Item</title>
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
            <h1>Edit Food Item</h1>

            <div class="card">
                <% if (item == null) { %>
                    <div class="empty-state">Food item not found.</div>
                <% } else { %>
                <form action="UpdateFoodItemServlet" method="post">
                    <input type="hidden" name="foodItemId" value="<%= item.getFoodItemID() %>">

                    <div class="field">
                        <label for="itemName">Item Name</label>
                        <input type="text" id="itemName" name="itemName" value="<%= item.getFoodItemName() %>" required>
                    </div>

                    <div class="field">
                        <label for="category">Category</label>
                        <input type="text" id="category" name="category" value="<%= item.getCategory() %>" required>
                    </div>

                    <div class="field">
                        <label for="foodItemType">Food Type</label>
                        <select id="foodItemType" name="foodItemType" required>
                            <% for (String t : foodItemTypes) { %>
                                <option value="<%= t %>" <%= t.equals(item.getFoodItemType()) ? "selected" : "" %>><%= t %></option>
                            <% } %>
                        </select>
                    </div>

                    <div class="field">
                        <label for="description">Description</label>
                        <textarea id="description" name="description"><%= item.getDescription() != null ? item.getDescription() : "" %></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Save Changes</button>
                    <a href="foodItemManagement.jsp" class="btn btn-secondary">Cancel</a>
                </form>
                <% } %>
            </div>
        </div>
    </body>
</html>
