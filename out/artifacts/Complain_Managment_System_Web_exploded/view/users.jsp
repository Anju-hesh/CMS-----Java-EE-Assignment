<%@ page import="lk.ijse.gdse72.model.podos.UserDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <title>All User List</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userpage.css">

</head>
<body>
<h1>All User List</h1>

<div style="margin-bottom: 30px;">
  <a href="<%=request.getContextPath()%>/view/admin-dashboard.jsp"
     style="
          position: absolute;
          left: 0;
          display: inline-block;
          padding: 10px 20px;
          background: #764ba2;
          color: white;
          border-radius: 8px;
          text-decoration: none;
          font-weight: 600;
          box-shadow: 0 4px 12px rgba(118,75,162,0.6);
       "
  >⬅ Back to Dashboard</a>
</div>

<div class="container">

  <div class="table">
    <h2>Admins</h2>
    <table border="1" cellpadding="8" cellspacing="0">
      <tr>
        <th>User ID</th>
        <th>Username</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Number</th>
      </tr>
      <%
        List<UserDTO> admins = (List<UserDTO>) request.getAttribute("adminsList");

        if (admins == null) {
      %>
      <tr><td colspan="5" style="color:red;">Admins list is NULL</td></tr>
      <%
      } else {
        for (UserDTO u : admins) {
      %>
      <tr>
        <td><%= u.getUserId() %></td>
        <td><%= u.getUsername() %></td>
        <td><%= u.getFullName() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getNumber() %></td>
      </tr>
      <%
          }
        }
      %>
    </table>
  </div>

  <div class="table" style="margin-top: 40px;">
    <h2>Employees</h2>
    <table border="1" cellpadding="8" cellspacing="0">
      <tr>
        <th>User ID</th>
        <th>Username</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Number</th>
        <th>Actions</th>
      </tr>
      <%
        List<UserDTO> employees = (List<UserDTO>) request.getAttribute("employeesList");

        if (employees == null) {
      %>
      <tr><td colspan="6" style="color:red;">Employees list is NULL</td></tr>
      <%
      } else {
        for (UserDTO u : employees) {
      %>
      <tr>
        <td><%= u.getUserId() %></td>
        <td><%= u.getUsername() %></td>
        <td><%= u.getFullName() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getNumber() %></td>
        <td>
          <form action="<%=request.getContextPath()%>/users" method="post" style="margin:0;">
            <input type="hidden" name="userId" value="<%= u.getUserId() %>">
            <input type="hidden" name="action" value="promote">
            <input type="submit" value="Make Admin" onclick="return confirm('Promote this user to Admin?');">
          </form>
        </td>
      </tr>
      <%
          }
        }
      %>
    </table>
  </div>
</div>

</body>
</html>
