<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Approve Donation</title>
    </head>
    <body>
        <h2>Approve Food Donation</h2>
        <form action="ApproveDonationServlet" method="POST">
            <input type="hidden" name="donationID" value="${param.donationID}" />
            <input type="hidden" name="foodItemID" value="${param.foodItemID}" />
            <input type="hidden" name="quantity" value="${param.quantity}" />
        
            <input type="hidden" name="staffID" value="${sessionScope.staffID}" />

            <p><strong>Donation ID to Approve:</strong> ${param.donationID}</p>
            <p><strong>Food Item ID:</strong> ${param.foodItemID}</p>
            <p><strong>Quantity:</strong> ${param.quantity}</p>

            <label for="expiryDate">Assign Inventory Expiry Date:</label>
            <input type="date" id="expiryDate" name="expiryDate" required><br><br>

            <input type="submit" value="Confirm Approval & Move to Inventory">
        </form>
    </body>
</html>
