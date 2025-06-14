package lk.ijse.gdse72.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.gdse72.model.ComplaintDAO;
import lk.ijse.gdse72.model.podos.ComplaintDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/dashboard-stats")
public class DashboardStatsServlet extends HttpServlet {

    private ComplaintDAO complaintDAO = new ComplaintDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get methid ekata awa AdminViewComplaintServlet ");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("session eka null welada koheda ..!");

            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        try {
            System.out.println("Before Call getAllComplaints In complaintDAO ...");
            List<ComplaintDTO> complaints = complaintDAO.getAllComplaints();
            System.out.println("After Call getAllComplaints In complaintDAO ..." + complaints);

            req.setAttribute("complaints", complaints);

            req.getRequestDispatcher("/view/view-all-complaints.jsp").forward(req, resp);

        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Some thing went wrong ..." + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load complaints at this time.");
        }
    }

    private Map<String, Object> calculateStatistics(List<ComplaintDTO> complaints) {
        Map<String, Object> stats = new HashMap<>();

        // Status counts
        Map<String, Integer> statusCounts = new HashMap<>();
        statusCounts.put("pending", 0);
        statusCounts.put("inProgress", 0);
        statusCounts.put("resolved", 0);
        statusCounts.put("rejected", 0);

        // Department counts
        Map<String, Integer> departmentCounts = new HashMap<>();

        // Priority counts
        Map<String, Integer> priorityCounts = new HashMap<>();
        priorityCounts.put("LOW", 0);
        priorityCounts.put("MEDIUM", 0);
        priorityCounts.put("HIGH", 0);

        // Process each complaint
        for (ComplaintDTO complaint : complaints) {
            // Count by status
            String status = complaint.getStatus().toLowerCase();
            if (status.equals("in_progress")) {
                status = "inProgress";
            }
            statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);

            // Count by department
            String department = complaint.getDepartment();
            departmentCounts.put(department, departmentCounts.getOrDefault(department, 0) + 1);

            // Count by priority
            String priority = complaint.getPriority();
            priorityCounts.put(priority, priorityCounts.getOrDefault(priority, 0) + 1);
        }

        // Recent activity (last 5 complaints)
        List<Map<String, String>> recentActivity = complaints.stream()
                .limit(5)
                .map(this::mapComplaintToActivity)
                .toList();


        stats.put("statusCounts", statusCounts);
        stats.put("departmentCounts", departmentCounts);
        stats.put("priorityCounts", priorityCounts);
        stats.put("recentActivity", recentActivity);
        stats.put("totalComplaints", complaints.size());

        return stats;
    }

    private Map<String, String> mapComplaintToActivity(ComplaintDTO complaint) {
        Map<String, String> activity = new HashMap<>();

        String text = String.format("Complaint #%s - %s (%s)",
                complaint.getComplaintId().substring(0, Math.min(8, complaint.getComplaintId().length())),
                complaint.getTitle().length() > 30 ?
                        complaint.getTitle().substring(0, 30) + "..." : complaint.getTitle(),
                complaint.getStatus());

        activity.put("text", text);
        activity.put("time", getTimeAgo(complaint.getCreatedAt()));

        return activity;
    }

    private String getTimeAgo(java.time.LocalDateTime dateTime) {
        if (dateTime == null) return "Unknown";

        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.Duration duration = java.time.Duration.between(dateTime, now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + " min ago";
        } else if (hours < 24) {
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        } else {
            return days + " day" + (days > 1 ? "s" : "") + " ago";
        }
    }

    private String convertToJson(Map<String, Object> stats) {
        StringBuilder json = new StringBuilder();
        json.append("{");

        // Status counts
        json.append("\"statusCounts\":{");
        Map<String, Integer> statusCounts = (Map<String, Integer>) stats.get("statusCounts");
        json.append("\"pending\":").append(statusCounts.get("pending")).append(",");
        json.append("\"inProgress\":").append(statusCounts.get("inProgress")).append(",");
        json.append("\"resolved\":").append(statusCounts.get("resolved")).append(",");
        json.append("\"rejected\":").append(statusCounts.get("rejected"));
        json.append("},");

        // Department counts
        json.append("\"departmentCounts\":{");
        Map<String, Integer> departmentCounts = (Map<String, Integer>) stats.get("departmentCounts");
        boolean first = true;
        for (Map.Entry<String, Integer> entry : departmentCounts.entrySet()) {
            if (!first) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":").append(entry.getValue());
            first = false;
        }
        json.append("},");

        // Priority counts
        json.append("\"priorityCounts\":{");
        Map<String, Integer> priorityCounts = (Map<String, Integer>) stats.get("priorityCounts");
        json.append("\"LOW\":").append(priorityCounts.get("LOW")).append(",");
        json.append("\"MEDIUM\":").append(priorityCounts.get("MEDIUM")).append(",");
        json.append("\"HIGH\":").append(priorityCounts.get("HIGH"));
        json.append("},");

        // Recent activity
        json.append("\"recentActivity\":[");
        List<Map<String, String>> recentActivity = (List<Map<String, String>>) stats.get("recentActivity");
        for (int i = 0; i < recentActivity.size(); i++) {
            if (i > 0) json.append(",");
            Map<String, String> activity = recentActivity.get(i);
            json.append("{");
            json.append("\"text\":\"").append(escapeJson(activity.get("text"))).append("\",");
            json.append("\"time\":\"").append(escapeJson(activity.get("time"))).append("\"");
            json.append("}");
        }
        json.append("],");

        // Total complaints
        json.append("\"totalComplaints\":").append(stats.get("totalComplaints"));

        json.append("}");
        return json.toString();
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}