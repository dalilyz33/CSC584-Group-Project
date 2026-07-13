<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Submit Food Donation</title>
    </head>
    <body>
        <h2>Submit a New Food Donation</h2>
        <form action="AddDonationServlet" method="POST">
            <input type="hidden" name="donorID" value="${sessionScope.donorID}" />

            <label for="foodItemId">Select Food Item ID:</label>
            <input type="number" id="foodItemID" name="foodItemID" required><br><br>

            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" min="1" required><br><br>

            <label for="dateApplication">Application Date:</label>
            <input type="date" id="dateApplication" name="dateApplication" required><br><br>

            <input type="submit" value="Submit Donation">
        </form>
    </body>
</html>
