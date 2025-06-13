<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Complaint Management System</title>
<%--    <link rel="stylesheet" href="../css/style.css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>Register</h1>
    <form action="${pageContext.request.contextPath}/registerservlet" method="post">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" required>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>

        <label for="fullName">Full Name:</label>
        <input type="text" name="fullName" id="fullName" required>

        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required>

        <label for="number">Phone Number:</label>
        <input type="text" name="number" id="number" required>

        <label for="role">Role:</label>
        <select name="role" id="role">
            <option value="EMPLOYEE">Employee</option>
            <option value="ADMIN">Admin</option>
        </select>

        <input type="submit" value="Register">
        <a href="<%= request.getContextPath() %>/index.jsp"
           style="position: absolute; right: 10px; top: 10px; background-color: #f44336; color: white; padding: 6px 12px; border-radius: 4px; text-decoration: none; cursor: pointer;">
            Back To Login ...
        </a>

    </form>
</div>
</body>
</html>