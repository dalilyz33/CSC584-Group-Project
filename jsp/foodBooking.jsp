<%-- 
    Document   : foodBooking
    Created on : Jul 13, 2026, 7:04:50 AM
    Author     : Ainaa
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Food Collection</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container">
    <div class="card">
        <h2>Book Food Collection</h2>

        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>

        <form action="BookFoodServlet" method="post">
            <label for="collectionDate">Collection Date</label>
            <input type="date" id="collectionDate" name="collectionDate" required>

            <label for="collectionTime">Collection Time Slot</label>
            <select id="collectionTime" name="collectionTime" required>
                <option value="">-- Select a time slot --</option>
                <option value="09:00 - 10:00">09:00 - 10:00</option>
                <option value="10:00 - 11:00">10:00 - 11:00</option>
                <option value="11:00 - 12:00">11:00 - 12:00</option>
                <option value="14:00 - 15:00">14:00 - 15:00</option>
                <option value="15:00 - 16:00">15:00 - 16:00</option>
            </select>

            <label for="remarks">Remarks (optional)</label>
            <textarea id="remarks" name="remarks" rows="3"></textarea>

            <button type="submit">Submit Booking</button>
            <a class="button" href="myBooking.jsp" style="background:#64748b;">View My Bookings</a>
        </form>
    </div>
</div>

</body>
</html>

