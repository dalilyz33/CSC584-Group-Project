<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - CareShare</title>
    <script>
        function toggleStudentFields() {
            var role = document.getElementById("role").value;
            var studentFields = document.getElementById("studentFields");
            var programCode = document.getElementById("programCode");
            var groupCode = document.getElementById("groupCode");

            if (role === "Student") {
                studentFields.style.display = "block";
                programCode.required = true;
                groupCode.required = true;
            } else {
                studentFields.style.display = "none";
                programCode.required = false;
                groupCode.required = false;
            }
        }
    </script>
</head>
<body onload="toggleStudentFields()">
    <h1>Create Account</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <p style="color:red;"><%= errorMessage %></p>
    <%
        }
    %>

    <form action="RegisterServlet" method="post">
        <label>Full Name:</label><br>
        <input type="text" name="fullName" required><br>

        <label>Email:</label><br>
        <input type="text" name="email" required><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br>

        <label>Register as:</label><br>
        <select name="role" id="role" onchange="toggleStudentFields()">
            <option value="Student">Student</option>
            <option value="Donor">Donor</option>
        </select><br>

        <div id="studentFields">
            <label>Program Code:</label><br>
            <input type="text" name="programCode" id="programCode"><br>

            <label>Group Code:</label><br>
            <input type="text" name="groupCode" id="groupCode"
                   placeholder="e.g. CDCS2404A"><br>
        </div>

        <br>
        <input type="submit" value="Register">
    </form>

    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</body>
</html>