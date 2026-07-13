<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.careshare.bean.DonationBean" %>
<%@page import="java.util.List" %>
<%
    List<DonationBean> donations = (List<DonationBean>) request.getAttribute("donations");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Donations</title>
    </head>
    <body>
        <header class="page-header">
            <div class="brand">CareShare &mdash; Donor Portal</div>
                <nav>
                    <a href="donorDashboard.jsp">Dashboard</a>
                    <a href="makeDonation.jsp">Make Donation</a>
                    <a href="ViewDonationServlet">View My Donations</a>
                </nav>
        </header>
        
        <div class="container">
            <h1>My Donations</h1>

            <div class="card">
                <% if (donations == null || donations.isEmpty()) { %>
                    <div class="empty-state">
                        You haven't made any donations yet. <a href="makeDonation.jsp">Make your first donation.</a>
                    </div>
                <% } else { %>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Food Item</th>
                            <th>Quantity</th>
                            <th>Expiry Date</th>
                            <th>Submitted</th>
                            <th>Status</th>
                            <th>Admin Remarks</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (DonationBean d : donations) {
                            String badgeClass = "badge-pending";
                            if ("APPROVED".equals(d.getStatus())) badgeClass = "badge-approved";
                            else if ("REJECTED".equals(d.getStatus())) badgeClass = "badge-rejected";
                        %>
                        <tr>
                            <td>#<%= d.getDonationID() %></td>
                            <td><%= d.getFoodItemName() %></td>
                            <td><%= d.getQuantity() %> <%= d.getUnit() %></td>
                            <td><%= d.getExpiryDate() != null ? d.getExpiryDate() : "-" %></td>
                            <td><%= d.getDonationDate() %></td>
                            <td><span class="badge <%= badgeClass %>"><%= d.getStatus() %></span></td>
                            <td><%= d.getRemarks() != null ? d.getRemarks() : "-" %></td>
                        </tr>
                            <% } %>
                    </tbody>
                </table>
                <% } %>
            </div>
        </div>
    </body>
</html>
