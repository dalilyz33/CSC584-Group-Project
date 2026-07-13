<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.careshare.dao.FoodItemDAO" %>
<%@ page import="com.careshare.bean.FoodItemBean" %>
<%@ page import="java.util.List" %>
<% List<FoodItemBean> foodItems = new FoodItemDAO().getAllFoodItems();%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Make a donation</title>
    </head>
    <body>
        <header class="page-header">
            <div class="brand">CareShare Donor Portal</div>
            <nav>
                <a href="makeDonation.jsp">Make donation</a>
                <a href="ViewDonationServlet">View my donations</a>
            </nav>
        </header>
        
        <div class="container">
            <h1>Make donation</h1>
            
            <%
                if (request.getAttribute("message") != null) {
            %>
                    <div class="alert alert-success"><%= request.getAttribute("message") %></div>
            <%  } 
            %>
            <%
                if (request.getAttribute("error") != null) {
            %>
                    <div class="alert alert-error"><%= request.getAttribute("error") %></div>
            <%    
                }
            %>
            
            <div class="card">
                <form action="AddDonationServlet" method="post">
                    <div class="field">
                        <label for="foodItemID">Food Item</label>
                        <select id="foodItemID" name="foodItemID" required>
                            <option value="" disabled selected>Select a food item</option>
                            <% for (FoodItemBean item : foodItems) { 
                            %>
                                <option value="<%= item.getFoodItemID() %>">
                                    <%= item.getFoodItemName() %> <<%= item.getFoodItemType() %>)
                                </option>
                            <% } 
                            %>
                        </select>
                    </div>
                        
                    <div class="field">
                        <label for="quantity">Quantity</label>
                        <input type="number" id="quantity" name="quantity" min="0.01" step="0.01" required>
                    </div>
                        
                        <div class="field">
                            <label for="expiryDate">Expiry date</label>
                            <input type="date" id="expiryDate" name="expiryDate">
                        </div>
                        
                        <div class="field">
                            <label for="description">Description</label>
                            <textarea id="description" name="description"></textarea>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Submit donation</button>
                </form>
            </div>
        </div>
    </body>
</html>
