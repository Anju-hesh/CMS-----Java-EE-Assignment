<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Complaint</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<div class="container">
    <h1>Submit Complaint</h1>
    <form action="${pageContext.request.contextPath}/employee/submit-complaint" method="post">
        <label for="title">Title:</label>
        <input type="text" name="title" id="title" required>

        <label for="description">Description:</label>
        <textarea name="description" rows="4" id="description" required></textarea>

        <label for="department">Department:</label>
        <input type="text" name="department" id="department" required>

        <label for="priority">Priority:</label>
        <select name="priority" id="priority">
            <option value="LOW">Low</option>
            <option value="MEDIUM">Medium</option>
            <option value="HIGH">High</option>
        </select>

        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>