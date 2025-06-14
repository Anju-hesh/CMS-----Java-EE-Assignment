<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Complaint Management System</title>
    <link rel="stylesheet" href="css/sign_in.css">
</head>
<body>
<div class="container">
    <h1>Login</h1>

    <% String error = request.getParameter("error"); %>
    <div id="serverError" style="color: red; text-align: center; margin-bottom: 1rem;">
        <% if ("invalid".equals(error)) { %>
        Invalid username or password.
        <% } else if ("role".equals(error)) { %>
        User role not recognized.
        <% } %>
    </div>

    <form action="login" method="post" id="loginForm">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" required>
        <span class="error" id="usernameError"></span>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>
        <span class="error" id="passwordError"></span>

        <input type="submit" value="Login">
    </form>

    <p style="text-align: center; margin-top: 1rem;">
        Don't have an account? <a href="view/register.jsp">Register here</a>
    </p>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%--<script src="js/loginValidation.js"></script>--%>
</body>
</html>
