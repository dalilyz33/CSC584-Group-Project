<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.careshare.bean.DonationBean" %>
<%@ page import="java.util.List" %>
<%
    List<DonationBean> donations = (List<DonationBean>) request.getAttribute("donations");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Review Donations</title>
    </head>
    <body>
        header class="page-header">
    <div class="brand">CareShare</div>
    <nav>
        <a href="ReviewDonationServlet">Review Donations</a>
        <a href="InventoryServlet">Inventory</a>
    </nav>
</header>

<div class="container">
    <h1>Review Donations</h1>

    <% if (request.getAttribute("message") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("message") %></div>
    <% } %>

    <div class="card">
        <% if (donations == null || donations.isEmpty()) { %>
            <div class="empty-state">No donations are currently pending review.</div>
        <% } else { %>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Donor</th>
                            <th>Food Item</th>
                            <th>Quantity</th>
                            <th>Expiry Date</th>
                            <th>Submitted</th>
                            <th>Remarks</th>
                            <th>Decision</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% for (DonationBean d : donations) { %>
                        <tr>
                            <td>#<%= d.getDonationID() %></td>
                            <td><%= d.getDonorName() %></td>
                            <td><%= d.getFoodItemName() %></td>
                            <td><%= d.getQuantity() %> <%= d.getUnit() %></td>
                            <td><%= d.getExpiryDate() != null ? d.getExpiryDate() : "-" %></td>
                            <td><%= d.getDonationDate() %></td>
                            <td><%= d.getRemarks() != null ? d.getRemarks() : "-" %></td>
                            <td>
                                <div class="actions-row">
                                    <form class="inline-form" action="ApproveDonationServlet" method="post">
                                        <input type="hidden" name="donationID" value="<%= d.getDonationID() %>">
                                        <input type="hidden" name="decision" value="APPROVED">
                                        <input type="hidden" name="remarks" value="Approved">
                                        <button type="submit" class="btn btn-approve">Approve</button>
                                    </form>
                                    <form class="inline-form" action="RejectDonationServlet" method="post">
                                        <input type="hidden" name="donationID" value="<%= d.getDonationID() %>">
                                        <input type="hidden" name="decision" value="REJECTED">
                                        <input type="hidden" name="remarks" value="Rejected">
                                        <button type="submit" class="btn btn-reject">Reject</button>
                                    </form>
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
