<%-- 
    Document   : adminDashboard
    Created on : Jul 13, 2026, 7:04:33 AM
    Author     : Ainaa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 16px;
            margin-bottom: 10px;
        }
        .stat-card {
            background: #f8fafc;
            border: 1px solid #d9e3ee;
            border-radius: 14px;
            padding: 20px;
            text-align: center;
        }
        .stat-card h3 {
            margin: 0 0 6px 0;
            font-size: 30px;
            color: #12355b;
        }
        .stat-card p {
            margin: 0;
            color: #64748b;
            font-weight: bold;
        }
        .badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 999px;
            font-size: 12px;
            font-weight: bold;
        }
        .badge-available { background: #e8faef; color: #08723c; }
        .badge-low_stock { background: #fff7ed; color: #c2410c; }
        .badge-expired { background: #ffeaea; color: #b12727; }
        .badge-distributed { background: #eef3f8; color: #475569; }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container">
    <div class="card">
        <h2>Dashboard</h2>

        <c:if test="${not empty successMessage}">
            <p class="message">${successMessage}</p>
        </c:if>

        <div class="stats-grid">
            <div class="stat-card">
                <h3>${stats.totalAvailableFood}</h3>
                <p>Total Available Food</p>
            </div>
            <div class="stat-card">
                <h3>${stats.foodDistributed}</h3>
                <p>Food Distributed</p>
            </div>
            <div class="stat-card">
                <h3>${stats.noOfRequests}</h3>
                <p>No. of Requests</p>
            </div>
        </div>
    </div>

    <div class="card" style="margin-top:20px;">
        <h2>Inventory</h2>
        <table>
            <tr>
                <th>No.</th>
                <th>Name</th>
                <th>Category</th>
                <th>Status</th>
                <th>Expiry Date</th>
            </tr>
            <c:forEach var="item" items="${inventoryList}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${item.itemName}</td>
                    <td>${item.category}</td>
                    <td>
                        <c:choose>
                            <c:when test="${item.status == 'AVAILABLE'}">
                                <span class="badge badge-available">${item.status}</span>
                            </c:when>
                            <c:when test="${item.status == 'LOW_STOCK'}">
                                <span class="badge badge-low_stock">${item.status}</span>
                            </c:when>
                            <c:when test="${item.status == 'EXPIRED'}">
                                <span class="badge badge-expired">${item.status}</span>
                            </c:when>
                            <c:when test="${item.status == 'DISTRIBUTED'}">
                                <span class="badge badge-distributed">${item.status}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge">${item.status}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${item.expiryDate}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty inventoryList}">
                <tr><td colspan="5">No inventory records found.</td></tr>
            </c:if>
        </table>
    </div>
</div>

</body>
</html>


