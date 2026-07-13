<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.careshare.dao.FoodItemDAO" %>
<%@ page import="com.careshare.bean.FoodItemBean" %>
<%@ page import="java.util.List" %>
<%
    List<FoodItemBean> foodItems = new FoodItemDAO().getAllFoodItems();
    Boolean flashSuccess = (Boolean) session.getAttribute("flashSuccess");
    String flashMessage = (String) session.getAttribute("flashMessage");
    session.removeAttribute("flashSuccess");
    session.removeAttribute("flashMessage");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Food Item Management</title>
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
            <h1>Food Item Management</h1>

            <% if (flashMessage != null) { %>
                <div class="alert <%= Boolean.TRUE.equals(flashSuccess) ? "alert-success" : "alert-error" %>">
                    <%= flashMessage %>
                </div>
            <% } %>

            <div class="card">
            <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
                <h2 style="margin:0;">Catalog</h2>
                <a href="addFoodItem.jsp" class="btn btn-primary">+ Add Food Item</a>
            </div>

            <% if (foodItems == null || foodItems.isEmpty()) { %>
                <div class="empty-state">No food items yet. Add the first one above.</div>
            <% } else { %>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Unit</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (FoodItemBean item : foodItems) { %>
                            <tr>
                                <td>#<%= item.getFoodItemID() %></td>
                                <td><%= item.getFoodItemName() %></td>
                                <td><%= item.getCategory() %></td>
                                <td><%= item.getUnit() %></td>
                                <td><%= item.getDescription() != null ? item.getDescription() : "-" %></td>
                                <td>    
                                    <div class="actions-row">
                                        <a class="btn btn-secondary" href="UpdateFoodItemServlet?foodItemId=<%= item.getFoodItemID() %>">Edit</a>
                                        <a class="btn btn-danger" href="DeleteFoodItemServlet?foodItemId=<%= item.getFoodItemID() %>"
                                            onclick="return confirm('Delete this food item? This cannot be undone.');">Delete</a>
                                    </div>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } %>
            </div>
        </div>
    </body>
</html>
