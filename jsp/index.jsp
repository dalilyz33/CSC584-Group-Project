<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <title>CareShare</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>

<%
    // request.getSession(false) returns the existing session, or null if
    // there isn't one. We use false (not the no-arg version) because simply
    // visiting the homepage shouldn't create a tracked session for someone
    // who's just browsing anonymously and has never logged in.
    HttpSession userSession = request.getSession(false);

    // A session can exist without the person being logged in (e.g. Java EE
    // creates sessions for other reasons too), so checking userRole
    // specifically confirms they actually went through LoginServlet.
    boolean isLoggedIn = (userSession != null && userSession.getAttribute("userRole") != null);

    // Only read fullName if we know they're logged in — otherwise
    // userSession itself might be null and calling a method on it would
    // throw a NullPointerException.
    String fullName = isLoggedIn ? (String) userSession.getAttribute("userFullName") : null;
%>

        <header>
            <h1>CareShare!</h1>
            <nav>
                <a href="index.jsp">Home</a>
                <br>
<%
    // This scriptlet chooses which nav links get sent to the browser.
    // Only one of the two blocks below actually reaches the HTML output —
    // the other is skipped entirely, it's not just hidden with CSS.
    if (isLoggedIn) {
%>
                <p>Welcome back, <%= fullName %>!</p>
                <a href="DashboardServlet">
                    <button type="button" style="padding: 10px 20px; font-size: 16px; cursor: pointer;">
                        Go to Dashboard
                    </button>
                </a>
                <a href="LogoutServlet">Logout</a>
<%
    } else {
%>
                <a href="login.jsp">Log In</a>
                <a href="register.jsp">
                    <button type="button" style="padding: 10px 20px; font-size: 16px; cursor: pointer;">
                        Create an Account
                    </button>
                </a>
<%
    }
%>
            </nav>
        </header>
        
        <main>
            <div>
                <h2>A Digital Food Bank & Voucher Distribution System</h2>
            </div>
            <div>
                <p>CareShare is a digital platform where students can get free food by having 
                    access to food bank and redeem vouchers. It also allows students to donate 
                    food, book food collections, and apply for assistance. CareShare aims to help 
                    students in need and provide access to food.
                </p>
            </div>
            <br>
            
            <footer>
                <small>&copy; 2026 CareShare. All Rights Reserved.</small>
            </footer>
        </main>
    </body>
</html>