<%@ page import="lk.ijse.gdse72.model.ComplaintDAO" %>
<%@ page import="lk.ijse.gdse72.model.podos.ComplaintDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="lk.ijse.gdse72.model.podos.UserDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Employee Dashboard</title>
    <link rel="stylesheet" href="../css/employeeDashBoard.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
</head>
<body>

<%
    String login = request.getParameter("login");
    if ("success".equals(login)) {
%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    Swal.fire({
        icon:'success',
        title:'Login Successful',
        text:'Welcome back!'
    });
</script>
<%
    }
%>

<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    ComplaintDAO complaintDAO = new ComplaintDAO();
    List<ComplaintDTO> complaints = complaintDAO.getComplaintsByUser(user.getUserId());

    int pendingCount = 0;
    int resolvedCount = 0;
    int inProgressCount = 0;
    int rejectedCount = 0;

    List<ComplaintDTO> complaintsWithRemarks = new ArrayList<>();

    if (complaints != null && !complaints.isEmpty()) {
        for (ComplaintDTO complaint : complaints) {
            String status = complaint.getStatus() != null ? complaint.getStatus().toLowerCase() : "pending";

            if ("pending".equals(status)) {
                pendingCount++;
            } else if ("resolved".equals(status)) {
                resolvedCount++;
            } else if ("in_progress".equals(status)) {
                inProgressCount++;
            } else if ("rejected".equals(status)) {
                rejectedCount++;
            }

            if (complaint.getAdminRemarks() != null && !complaint.getAdminRemarks().isEmpty()) {
                complaintsWithRemarks.add(complaint);
            }
        }
    }
%>

<div class="dashboard-container">

    <aside class="sidebar glass">
        <div class="logo">
            <h1><i class="fas fa-building"></i> Portal</h1>
            <p>Employee Dashboard</p>
        </div>

        <div class="welcome-section">
            <h2>Hello,</h2>
            <span><%= user.getFullName() != null ? user.getFullName() : "User" %></span>
        </div>

        <nav>
            <ul class="nav-menu">
                <li class="nav-item">
                    <a href="#dashboard" class="nav-link active">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="complaint-form.jsp" class="nav-link">
                        <i class="nav-icon fas fa-plus-circle"></i>
                        <span>New Complaint</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="my-complaints.jsp" class="nav-link">
                        <i class="nav-icon fas fa-list-alt"></i>
                        <span>My Complaints</span>
                    </a>
                </li>
            </ul>
        </nav>

        <a href="<%= request.getContextPath() %>/logout" class="logout-btn">
            <i class="fas fa-sign-out-alt"></i> Logout
        </a>
    </aside>

    <main class="main-content">
        <header class="header glass">
            <h1>Dashboard Overview</h1>
            <p>Monitor your complaints and track their progress</p>
        </header>

        <div class="stats-grid">
            <div class="stat-card glass pending">
                <div class="stat-number"><%= pendingCount %></div>
                <div class="stat-label">
                    <i class="fas fa-clock"></i> Pending
                </div>
            </div>
            <div class="stat-card glass in-progress">
                <div class="stat-number"><%= inProgressCount %></div>
                <div class="stat-label">
                    <i class="fas fa-cog fa-spin"></i> In Progress
                </div>
            </div>
            <div class="stat-card glass resolved">
                <div class="stat-number"><%= resolvedCount %></div>
                <div class="stat-label">
                    <i class="fas fa-check-circle"></i> Resolved
                </div>
            </div>
            <div class="stat-card glass rejected">
                <div class="stat-number"><%= rejectedCount %></div>
                <div class="stat-label">
                    <i class="fas fa-times-circle"></i> Rejected
                </div>
            </div>
        </div>

        <div class="charts-grid">
            <div class="chart-container glass">
                <h3 class="chart-title">
                    <i class="fas fa-chart-pie"></i> Status Overview
                </h3>
                <div class="visual-stats">
                    <div class="visual-stat pending">
                        <div class="visual-bar" style="height: <%= pendingCount * 75 %>px"></div>
                        <span class="visual-label">Pending</span>
                    </div>
                    <div class="visual-stat in-progress">
                        <div class="visual-bar" style="height: <%= inProgressCount * 75 %>px"></div>
                        <span class="visual-label">In Progress</span>
                    </div>
                    <div class="visual-stat resolved">
                        <div class="visual-bar" style="height: <%= resolvedCount * 75 %>px"></div>
                        <span class="visual-label">Resolved</span>
                    </div>
                    <div class="visual-stat rejected">
                        <div class="visual-bar" style="height: <%= rejectedCount * 75 %>px"></div>
                        <span class="visual-label">Rejected</span>
                    </div>
                </div>
            </div>

            <div class="chart-container glass">
                <h3 class="chart-title">
                    <i class="fas fa-chart-line"></i> Progress Summary
                </h3>
                <div class="progress-summary">
                    <div class="progress-item">
                        <div class="progress-label">
                            <i class="fas fa-tasks"></i>
                            Total Complaints
                        </div>
                        <div class="progress-value">
                            <%= complaints != null ? complaints.size() : 0 %>
                        </div>
                    </div>
                    <div class="progress-item">
                        <div class="progress-label">
                            <i class="fas fa-percentage"></i>
                            Resolution Rate
                        </div>
                        <div class="progress-value">
                            <%= complaints != null && complaints.size() > 0 ?
                                    Math.round((double) resolvedCount / complaints.size() * 100) : 0 %>%
                        </div>
                    </div>
                    <div class="progress-item">
                        <div class="progress-label">
                            <i class="fas fa-clock"></i>
                            Pending Items
                        </div>
                        <div class="progress-value">
                            <%= pendingCount %>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="recent-activity glass">
            <h3 class="activity-title">
                <i class="fas fa-history"></i> Recent Admin Remarks
            </h3>

            <div class="activity-list">
                <%
                    if (!complaintsWithRemarks.isEmpty()) {
                        int maxRemarks = Math.min(5, complaintsWithRemarks.size());
                        for (int i = complaintsWithRemarks.size() - 1; i >= complaintsWithRemarks.size() - maxRemarks; i--) {
                            ComplaintDTO complaint = complaintsWithRemarks.get(i);
                            String status = complaint.getStatus() != null ? complaint.getStatus().toLowerCase() : "pending";
                            String statusClass = status.replace("_", "-");
                            String statusIcon = "";

                            switch(status) {
                                case "pending": statusIcon = "fas fa-clock"; break;
                                case "in_progress": statusIcon = "fas fa-cog fa-spin"; break;
                                case "resolved": statusIcon = "fas fa-check-circle"; break;
                                case "rejected": statusIcon = "fas fa-times-circle"; break;
                                default: statusIcon = "fas fa-info-circle";
                            }
                %>
                <div class="activity-item <%= statusClass %>">
                    <div class="activity-icon">
                        <i class="<%= statusIcon %>"></i>
                    </div>
                    <div class="activity-content">
                        <div class="activity-header">
                            <span class="complaint-id">Complaint #<%= complaint.getComplaintId() %></span>
                            <span class="activity-status <%= statusClass %>">
                                <%= status.replace("_", " ").toUpperCase() %>
                            </span>
                        </div>
                        <div class="activity-subject">
                            <i class="fas fa-tag"></i> <%= complaint.getTitle() != null ? complaint.getTitle() : "General" %>
                        </div>
                        <div class="activity-text">
                            <i class="fas fa-comment"></i> <%= complaint.getAdminRemarks() %>
                        </div>
                    </div>
                    <div class="activity-time">
                        <i class="fas fa-calendar"></i>
                        <%= complaint.getCreatedAt() != null ? complaint.getCreatedAt().toString() : "N/A" %>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <div class="empty-state">
                    <div class="empty-icon">
                        <i class="fas fa-inbox"></i>
                    </div>
                    <p>No admin remarks available yet.</p>
                    <small>Remarks will appear here when admins respond to your complaints.</small>
                </div>
                <%
                    }
                %>
            </div>
        </div>

        <div class="quick-stats glass">
            <h3 class="stats-title">
                <i class="fas fa-chart-bar"></i> Quick Statistics
            </h3>
            <div class="stats-row">
                <div class="quick-stat">
                    <i class="fas fa-list"></i>
                    <span class="stat-value"><%= complaints != null ? complaints.size() : 0 %></span>
                    <span class="stat-text">Total Complaints</span>
                </div>
                <div class="quick-stat">
                    <i class="fas fa-percentage"></i>
                    <span class="stat-value">
                        <%= complaints != null && complaints.size() > 0 ?
                                Math.round((double) resolvedCount / complaints.size() * 100) : 0 %>%
                    </span>
                    <span class="stat-text">Resolution Rate</span>
                </div>
                <div class="quick-stat">
                    <i class="fas fa-comments"></i>
                    <span class="stat-value"><%= complaintsWithRemarks.size() %></span>
                    <span class="stat-text">Admin Responses</span>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>
