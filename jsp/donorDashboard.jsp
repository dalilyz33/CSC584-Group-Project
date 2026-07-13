<%-- 
    Document   : donorDashboard
    Created on : Jul 13, 2026, 7:29:09 AM
    Author     : nurli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Donor Dashboard</title>
    </head>
    <body>
        <header class="page-header">
            <div class="brand">CareShare Donor Portal</div>
            <nav>
                <a href="donorDashboard.jsp">Dashboard</a>
                <a href="makeDonation.jsp">Make donation</a>
                <a href="ViewDonationServlet">View donations</a>
            </nav>
        </header>
        
        <div class="container">
            <h1>Welcome back ${empty sessionScope.donorName ? "Donor" : sessionScope.donorName}</h1>
            <p>Thank you for supporting the community. Choose an action below.</p>
            
            <div class="dashboard-grid">
                <a class="dashboard-title" href="makeDonation.jsp">
                    <div class="title-title">Make a donation</div>
                    <div class="title-disc">Submit a new food donation</div>
                </a>
                <a class="dashboard-title" href="ViewDonationServlet">
                    <div class="title-title">View donations</div>
                    <div class="title-desc">Track donation status</div>
                </a>
            </div>
        </div>
    </body>
</html>
