<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareShare - Announcement Dashboard</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="navbar">
        <div>CareShare Portal</div>
        <div>
            <a href="ViewFoodItemServlet?role=admin">Food Items</a>
            <a href="ViewBookingServlet?action=adminView">Bookings</a>
            <a href="sendNotification.jsp">Send Notification</a>
            <a href="NotificationCategoryServlet">Notification Categories</a>
        </div>
    </div>
    <div class="container">
        <div class="card" style="max-width: 600px; margin: 30px auto;">
            <h2>Broadcast System Notification</h2>
            
            <% if("success".equals(request.getParameter("status"))) { %>
                <div class="message">Alert broadcasted successfully to all target roles.</div>
            <% } %>

            <form action="SendNotificationServlet" method="POST" style="margin-top: 15px;">
                <label>Target Audience Cluster</label>
                <select name="roleTarget">
                    <option value="Student">All Registered Students</option>
                    <option value="Donor">All Registered Donors</option>
                    <option value="Admin">System Admins Only</option>
                </select>

                <label>Notification Context Category ID</label>
                <input type="number" name="notificationCategoryId" required>

                <label>Message Content</label>
                <textarea name="notificationMessage" rows="5" placeholder="Type system announcement text here..." required></textarea>

                <button type="submit">Transmit Announcement</button>
            </form>
        </div>
    </div>
</body>
</html>
