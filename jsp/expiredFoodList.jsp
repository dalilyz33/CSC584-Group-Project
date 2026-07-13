<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Expired Food List</title>
    </head>
    <body>
        <h2>⚠️ Expired Food Tracking List</h2>
    
        <a href="inventoryList.jsp" class="back-btn">← Back to Active Inventory</a>

        <c:if test="${not empty param.error}">
            <p class="alert-text">Error: ${param.error}</p>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>Inventory ID</th>
                    <th>Food Item ID</th>
                    <th>Food Item Name</th>
                    <th>Donation ID</th>
                    <th>Quantity</th>
                    <th>Expiry Date</th>
                    <th>Current Status</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty expiredFoodList}">
                        <c:forEach var="item" items="${expiredFoodList}">
                            <tr>
                                <td><c:out value="${item.inventoryID}" /></td>
                                <td><c:out value="${item.foodItemID}" /></td>
                                <td><c:out value="${item.foodItemName}" /></td>
                                <td><c:out value="${item.donationID}" /></td>
                                <td><c:out value="${item.quantity}" /></td>
                                <td class="alert-text"><c:out value="${item.expiryDate}" /></td>
                                <td>
                                    <span class="alert-text"><c:out value="${item.foodStatus}" /></span>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                                
                    <c:otherwise>
                        <tr>
                            <td colspan="7" style="text-align: center; color: #777;">No expired food items found in inventory tracking.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </body>
</html>
