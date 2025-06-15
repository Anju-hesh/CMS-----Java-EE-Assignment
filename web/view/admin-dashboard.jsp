<%@ page import="lk.ijse.gdse72.model.podos.ComplaintDTO" %>
<%@ page import="lk.ijse.gdse72.model.ComplaintDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.gdse72.model.podos.UserDTO" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Complaint Management</title>
    <link rel="stylesheet" href="../css/addminDashboard.css">

</head>
<body>
<div class="dashboard-container" id="dashboard">
    <%
        UserDTO user = (UserDTO) session.getAttribute("user");

        List<ComplaintDTO> users= null;
        if (user != null) {
            ComplaintDAO complaintDAO = new ComplaintDAO();
            users = complaintDAO.getComplaintsByUser(user.getUserId());
        }
    %>

    <nav class="sidebar glass">
        <div class="logo">
            <h1>📋 CMS</h1>
            <p>Complaint Management</p>
        </div>

        <div class="welcome-section">
            <h2>Welcome,</h2>
            <% if (user != null) { %>
            <h2><span><%= user.getUsername() %></span></h2>
            <% } else { %>
            <h2><span>Guest</span></h2>
            <% } %>
        </div>
        <div>
        <ul class="nav-menu">
            <li class="nav-item">
                <a href="#dashboard" class="nav-link">
                    <span class="nav-icon">📊</span>
                    Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/view/view-all-complaints.jsp" class="nav-link">
                    <span class="nav-icon">📋</span>
                    All Complaints
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link">
                    <span class="nav-icon">📈</span>
                    Reports
                </a>
            </li>
<%--            <li class="nav-item">--%>
<%--                <a href="${pageContext.request.contextPath}/view/users.jsp" class="nav-link">--%>
<%--                    <span class="nav-icon">👥</span>--%>
<%--                    Users--%>
<%--                </a>--%>
<%--            </li>--%>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/users" class="nav-link">
                    <span class="nav-icon">👥</span>
                    Users
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link">
                    <span class="nav-icon">⚙️</span>
                    Settings
                </a>
            </li>
        </ul>
        </div>

<%--        <button class="logout-btn" href="${pageContext.request.contextPath}/logout">--%>
<%--            <span class="nav-icon">🚪</span>--%>
<%--            logout--%>
<%--        </button>--%>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn" style="text-decoration: none;">
            <span class="nav-icon">🚪</span>
            Logout
        </a>
    </nav>

    <%
        System.out.println("before get complaints: ");
        ComplaintDAO complaintDAO = new ComplaintDAO();
        List<ComplaintDTO> complaints = complaintDAO.getAllComplaints();
        System.out.println("complaints tika: " + complaints);

        int totalComplaints = 0;
        int pendingCount = 0;
        int resolvedCount = 0;
        int inProgressCount = 0;
        int rejectedCount = 0;

        if (complaints != null) {
            totalComplaints = complaints.size();

            for (ComplaintDTO complaint : complaints) {
                String status = complaint.getStatus() != null ? complaint.getStatus().toLowerCase() : "";

                if ("pending".equals(status)) pendingCount++;
                else if ("resolved".equals(status)) resolvedCount++;
                else if ("in_progress".equals(status)) inProgressCount++;
                else if ("rejected".equals(status)) rejectedCount++;
            }
        }
    %>

    <main class="main-content">
        <div class="header glass">
            <h1>Admin Dashboard</h1>
            <p>Monitor and manage complaint system performance</p>
        </div>

        <div class="stats-grid">
            <div class="stat-card glass pending">
                <div class="stat-number"><%= pendingCount %></div>
                <div class="stat-label">Pending</div>
            </div>
            <div class="stat-card glass in-progress">
                <div class="stat-number"><%= inProgressCount %></div>
                <div class="stat-label">In Progress</div>
            </div>
            <div class="stat-card glass resolved">
                <div class="stat-number"><%= resolvedCount %></div>
                <div class="stat-label">Resolved</div>
            </div>
            <div class="stat-card glass rejected">
                <div class="stat-number"><%= rejectedCount %></div>
                <div class="stat-label">Rejected</div>
            </div>
        </div>

        <div class="charts-grid">
            <div class="chart-container glass">
                <h3 class="chart-title">Status Distribution</h3>
                <div style="color: white; text-align: center; margin-top: 50px;">
                    Chart functionality would be displayed here
                </div>
            </div>
            <div class="chart-container glass">
                <h3 class="chart-title">Department Overview</h3>
                <div style="color: white; text-align: center; margin-top: 50px;">
                    Chart functionality would be displayed here
                </div>
            </div>
        </div>

        <div class="recent-activity glass">
            <h3 class="activity-title">Recent Activity</h3>
            <div id="recentActivity">
                <div class="activity-item">
                    <div class="activity-text">No recent activities to display</div>
                    <div class="activity-time"></div>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>