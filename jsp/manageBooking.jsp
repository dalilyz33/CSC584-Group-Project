<%-- 
    Document   : manageBooking
    Created on : Jul 13, 2026, 7:05:19 AM
    Author     : Ainaa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Bookings</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container">
    <div class="card">
        <h2>Manage Food Collection Bookings</h2>

        <c:if test="${not empty successMessage}">
            <p class="message">${successMessage}</p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>

        <form action="ViewBookingServlet" method="get">
            <label for="status">Filter by status</label>
            <select id="status" name="status" onchange="this.form.submit()">
                <option value="ALL">All</option>
                <option value="PENDING">Pending</option>
                <option value="APPROVED">Approved</option>
                <option value="REJECTED">Rejected</option>
                <option value="COMPLETED">Completed</option>
                <option value="CANCELLED">Cancelled</option>
            </select>
        </form>

        <table>
            <tr>
                <th>Student</th>
                <th>Booking Date</th>
                <th>Collection Date</th>
                <th>Time Slot</th>
                <th>Remarks</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <c:forEach var="b" items="${bookings}">
                <tr>
                    <td>${b.studentName}</td>
                    <td>${b.bookingDate}</td>
                    <td>${b.collectionDate}</td>
                    <td>${b.collectionTime}</td>
                    <td>${b.remarks}</td>
                    <td>${b.status}</td>
                    <td>
                        <c:if test="${b.status == 'PENDING'}">
                            <form action="UpdateBookingServlet" method="post" style="display:inline;">
                                <input type="hidden" name="bookingId" value="${b.bookingId}">
                                <input type="hidden" name="status" value="APPROVED">
                                <button type="submit" class="approve">Approve</button>
                            </form>
                            <form action="UpdateBookingServlet" method="post" style="display:inline;">
                                <input type="hidden" name="bookingId" value="${b.bookingId}">
                                <input type="hidden" name="status" value="REJECTED">
                                <button type="submit" class="reject">Reject</button>
                            </form>
                        </c:if>
                        <c:if test="${b.status == 'APPROVED'}">
                            <form action="UpdateBookingServlet" method="post" style="display:inline;">
                                <input type="hidden" name="bookingId" value="${b.bookingId}">
                                <input type="hidden" name="status" value="COMPLETED">
                                <button type="submit">Mark Completed</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty bookings}">
                <tr><td colspan="7">No bookings found.</td></tr>
            </c:if>
        </table>
    </div>
</div>

</body>
</html>
