<%-- 
    Document   : notification
    Created on : Jul 13, 2026, 7:05:48 AM
    Author     : Ainaa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Notifications</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .notif-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .notif-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 12px;
            padding: 14px 0;
            border-bottom: 1px solid #e5ebf2;
        }
        .notif-item:last-child {
            border-bottom: none;
        }
        .notif-item.unread {
            font-weight: bold;
        }
        .notif-text {
            flex: 1;
        }
        .notif-date {
            color: #64748b;
            font-size: 12px;
            font-weight: normal;
            white-space: nowrap;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container">
    <div class="card">
        <h2>Notifications</h2>

        <c:if test="${not empty successMessage}">
            <p class="message">${successMessage}</p>
        </c:if>

        <c:if test="${not empty notifications}">
            <form action="NotificationServlet" method="post" style="margin-bottom:16px;">
                <input type="hidden" name="action" value="markAllRead">
                <button type="submit">Mark All As Read</button>
            </form>
        </c:if>

        <ul class="notif-list">
            <c:forEach var="n" items="${notifications}">
                <li class="notif-item ${n.read ? '' : 'unread'}">
                    <span class="notif-text">${n.message}</span>
                    <span class="notif-date">${n.dateSent}</span>
                    <c:if test="${!n.read}">
                        <form action="NotificationServlet" method="post" style="margin:0;">
                            <input type="hidden" name="action" value="markRead">
                            <input type="hidden" name="notificationId" value="${n.notificationId}">
                            <button type="submit" style="padding:6px 10px;">Mark Read</button>
                        </form>
                    </c:if>
                </li>
            </c:forEach>
            <c:if test="${empty notifications}">
                <li class="notif-item">No notifications yet.</li>
            </c:if>
        </ul>
    </div>
</div>

</body>
</html>

