<%-- 
    Document   : myBooking
    Created on : Jul 13, 2026, 7:05:33 AM
    Author     : Ainaa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Bookings</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container">
    <div class="card">
        <h2>My Food Collection Bookings</h2>

        <c:if test="${not empty successMessage}">
            <p class="message">${successMessage}</p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>

        <p><a class="button" href="foodBooking.jsp">+ New Booking</a></p>

        <table>
            <tr>
                <th>Booking Date</th>
                <th>Collection Date</th>
                <th>Time Slot</th>
                <th>Remarks</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <c:forEach var="b" items="${bookings}">
                <tr>
                    <td>${b.bookingDate}</td>
                    <td>${b.collectionDate}</td>
                    <td>${b.collectionTime}</td>
                    <td>${b.remarks}</td>
                    <td>${b.status}</td>
                    <td>
                        <c:if test="${b.status == 'PENDING' || b.status == 'APPROVED'}">
                            <form action="CancelBookingServlet" method="post" style="margin:0;">
                                <input type="hidden" name="bookingId" value="${b.bookingId}">
                                <button type="submit" class="reject" onclick="return confirm('Cancel this booking?');">Cancel</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty bookings}">
                <tr><td colspan="6">No bookings found.</td></tr>
            </c:if>
        </table>
    </div>
</div>

</body>
</html>
