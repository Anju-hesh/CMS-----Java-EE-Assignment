<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Dashboard</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<div class="container">
    <div class="navbar">
        <a href="complaint-form.jsp">Submit Complaint</a>
        <a href="my-complaints.jsp">My Complaints</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
    <h1>Welcome, ${sessionScope.username}</h1>
</div>
</body>
</html>