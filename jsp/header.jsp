<%-- 
    Document   : header
    Created on : Jul 13, 2026, 7:05:03 AM
    Author     : Ainaa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar">
    <div><strong>CareShare</strong></div>
    <div>
        <c:if test="${sessionScope.role == 'STUDENT'}">
            <a href="studentDashboard.jsp">Dashboard</a>
            <a href="foodBooking.jsp">Book Food Collection</a>
            <a href="myBooking.jsp">My Bookings</a>
            <a href="NotificationServlet">Notifications</a>
        </c:if>
        <c:if test="${sessionScope.role == 'ADMIN'}">
            <a href="AdminDashboardServlet">Dashboard</a>
            <a href="ViewBookingServlet">Manage Bookings</a>
            <a href="NotificationServlet">Notifications</a>
        </c:if>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

