<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Login - Complaint Management System</title>--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>--%>
<%--    <link rel="stylesheet" href="css/sign_in.css">--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>
<%--    <h1>Login</h1>--%>

<%--&lt;%&ndash;    <% String error = (String) request.getAttribute("error"); %>&ndash;%&gt;--%>
<%--&lt;%&ndash;    <% if (error != null) { %>&ndash;%&gt;--%>
<%--&lt;%&ndash;    <script>&ndash;%&gt;--%>
<%--&lt;%&ndash;        Swal.fire({&ndash;%&gt;--%>
<%--&lt;%&ndash;            icon: 'error',&ndash;%&gt;--%>
<%--&lt;%&ndash;            title: 'Login Failed',&ndash;%&gt;--%>
<%--&lt;%&ndash;            text:'<%= error %>',&ndash;%&gt;--%>
<%--&lt;%&ndash;            confirmButtonText:'OK'&ndash;%&gt;--%>
<%--&lt;%&ndash;        });&ndash;%&gt;--%>
<%--&lt;%&ndash;    </script>&ndash;%&gt;--%>
<%--&lt;%&ndash;    <% } %>&ndash;%&gt;--%>

<%--    <% String error = request.getParameter("error"); %>--%>
<%--    <div id="serverError" style="color: red; text-align: center; margin-bottom: 1rem;">--%>
<%--        <% if ("invalid".equals(error)) { %>--%>
<%--        Invalid username or password.--%>
<%--        <% } else if ("role".equals(error)) { %>--%>
<%--        User role not recognized.--%>
<%--        <% } %>--%>
<%--    </div>--%>

<%--    <form action="login" method="post" id="loginForm">--%>
<%--        <label for="username">Username:</label>--%>
<%--        <input type="text" name="username" id="username" required>--%>
<%--        <span class="error" id="usernameError"></span>--%>

<%--        <label for="password">Password:</label>--%>
<%--        <input type="password" name="password" id="password" required>--%>
<%--        <span class="error" id="passwordError"></span>--%>

<%--        <input type="submit" value="Login">--%>
<%--    </form>--%>

<%--    <p style="text-align: center; margin-top: 1rem;">--%>
<%--        Don't have an account? <a href="view/register.jsp">Register here</a>--%>
<%--    </p>--%>
<%--</div>--%>

<%--<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>--%>
<%--&lt;%&ndash;<script src="js/loginValidation.js"></script>&ndash;%&gt;--%>
<%--<script>--%>
<%--    $(document).ready(function () {--%>

<%--        let serverError = "<%= request.getParameter("error") %>";--%>

<%--        if (serverError === "invalid") {--%>

<%--            $("#username").addClass("invalid");--%>
<%--            $("#usernameError").text("Invalid Username.");--%>
<%--            $("#password").addClass("invalid");--%>
<%--            $("#passwordError").text("Invalid Password.");--%>
<%--        }--%>
<%--    });--%>
<%--</script>--%>
<%--</body>--%>
<%--</html>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Complaint Management System</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/sign_in.css">
</head>
<body>
<div class="container">
    <h1>Login</h1>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
    <script>
        Swal.fire({
            icon:'error',
            title:'Login Failed',
            text:'<%= "invalid".equals(error) ? "Invalid Username or Password" : "User role not recognized" %>',
            confirmButtonText:'OK'
        });
    </script>
    <% } %>

    <form action="login" method="post" id="loginForm">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" required>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>

        <input type="submit" value="Login">
    </form>

    <p style="text-align: center; margin-top: 1rem">
        Don't have an account? <a href="view/register.jsp">Register here</a>
    </p>
</div>
</body>
</html>
