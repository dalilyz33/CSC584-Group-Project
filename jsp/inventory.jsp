<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.careshare.bean.InventoryBean" %>
<%@ page import="java.util.List" %>
<%
    List<InventoryBean> inventory = (List<InventoryBean>) request.getAttribute("inventory");
    Boolean flashSuccess = (Boolean) session.getAttribute("flashSuccess");
    String flashMessage = (String) session.getAttribute("flashMessage");
    session.removeAttribute("flashSuccess");
    session.removeAttribute("flashMessage");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory</title>
    </head>
    <body>
        <header class="page-header">
            <div class="brand">CareShare</div>
            <nav>
                <a href="ReviewDonationServlet">Review Donations</a>
                <a href="InventoryServlet">Inventory</a>
            </nav>
        </header>

        <div class="container">
            <h1>Inventory</h1>

            <% if (flashMessage != null) { %>
                <div class="alert <%= Boolean.TRUE.equals(flashSuccess) ? "alert-success" : "alert-error" %>">
                    <%= flashMessage %>
                </div>
            <% } %>

            <div class="card">
            <% if (inventory == null || inventory.isEmpty()) { %>
                <div class="empty-state">No inventory yet. Stock is created automatically when a donation is approved.</div>
            <% } else { %>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Food Item</th>
                            <th>Quantity</th>
                            <th>Expiry Date</th>
                            <th>Status</th>
                            <th>Last Updated</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (InventoryBean inv : inventory) {
                            String badgeClass = "badge-available";
                            if ("EXPIRED".equals(inv.getFoodStatus())) badgeClass = "badge-expired";
                            else if ("DISTRIBUTED".equals(inv.getFoodStatus())) badgeClass = "badge-distributed";
                        %>
                        <tr>
                            <td>#<%= inv.getInventoryID() %></td>
                            <td><%= inv.getFoodItemName() %></td>
                            <td><%= inv.getQuantity() %></td>
                            <td><%= inv.getExpiryDate() != null ? inv.getExpiryDate() : "-" %></td>
                            <td><span class="badge <%= badgeClass %>"><%= inv.getFoodStatus() %></span></td>
                            <td>
                                <form class="inline-form" action="UpdateInventoryServlet" method="post"
                                    style="display:flex; gap:6px; align-items:center; flex-wrap:wrap;">
                                    <input type="hidden" name="inventoryId" value="<%= inv.getInventoryID() %>">
                                    
                                    <input type="number" name="quantity" value="<%= inv.getQuantity() %>"
                                        min="0" step="0.01" style="width:90px;" required>
                                    
                                    <input type="date" name="expiryDate"
                                        value="<%= inv.getExpiryDate() != null ? inv.getExpiryDate() : "" %>"
                                        style="width:150px;">
                                    
                                        <select name="foodStatus" style="width:130px;">
                                            <option value="AVAILABLE" <%= "AVAILABLE".equals(inv.getFoodStatus()) ? "selected" : "" %>>AVAILABLE</option>
                                            <option value="EXPIRED" <%= "EXPIRED".equals(inv.getFoodStatus()) ? "selected" : "" %>>EXPIRED</option>
                                            <option value="DISTRIBUTED" <%= "DISTRIBUTED".equals(inv.getFoodStatus()) ? "selected" : "" %>>DISTRIBUTED</option>
                                        </select>
                                    <button type="submit" class="btn btn-secondary">Save</button>
                                </form>
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
