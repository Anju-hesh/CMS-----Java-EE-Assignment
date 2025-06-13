//package lk.ijse.gdse72.model;
//
//import lk.ijse.gdse72.model.podos.ComplaintDTO;
//import lk.ijse.gdse72.util.DatabaseConfig;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ComplaintDAO {
//
//    public boolean createComplaint(ComplaintDTO complaint) {
//        String sql = "INSERT INTO complaints (complaint_id, title, description, department, " +
//                "priority, status, submitted_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, complaint.getComplaintId());
//            pstmt.setString(2, complaint.getTitle());
//            pstmt.setString(3, complaint.getDescription());
//            pstmt.setString(4, complaint.getDepartment());
//            pstmt.setString(5, complaint.getPriority());
//            pstmt.setString(6, complaint.getStatus());
//            pstmt.setString(7, complaint.getSubmittedBy());
//
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("[ComplaintDAO] Error creating complaint: " + e.getMessage());
//            return false;
//        }
//    }
//
//
//    public List<ComplaintDTO> getComplaintsByUser(String userId) {
//        List<ComplaintDTO> complaints = new ArrayList<>();
//        String sql = "SELECT c.*, u.full_name as submitted_by_name, " +
//                "a.full_name as assigned_to_name FROM complaints c " +
//                "LEFT JOIN users u ON c.submitted_by = u.user_id " +
//                "LEFT JOIN users a ON c.assigned_to = a.user_id " +
//                "WHERE c.submitted_by = ? ORDER BY c.created_at DESC";
//
//        System.out.println("[DEBUG] SQL Query: " + sql);
//        System.out.println("[DEBUG] User ID parameter: " + userId);
//
//        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, userId);
//            System.out.println("[DEBUG] About to execute query...");
//
//            ResultSet rs = pstmt.executeQuery();
//            System.out.println("[DEBUG] Query executed successfully");
//
//            int count = 0;
//            while (rs.next()) {
//                count++;
//                System.out.println("[DEBUG] Processing row " + count);
//                System.out.println("[DEBUG] Complaint ID: " + rs.getString("complaint_id"));
//                System.out.println("[DEBUG] Title: " + rs.getString("title"));
//
//                complaints.add(mapResultSetToComplaint(rs));
//            }
//
//            System.out.println("[DEBUG] Total complaints found: " + complaints.size());
//
//        } catch (SQLException e) {
//            System.err.println("[ComplaintDAO] SQLException: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return complaints;
//    }
//
//        private ComplaintDTO mapResultSetToComplaint(ResultSet rs) throws SQLException {
//            ComplaintDTO complaint = new ComplaintDTO();
//            complaint.setComplaintId(rs.getString("complaint_id"));
//            complaint.setTitle(rs.getString("title"));
//            complaint.setDescription(rs.getString("description"));
//            complaint.setDepartment(rs.getString("department"));
//            complaint.setPriority(rs.getString("priority"));
//            complaint.setStatus(rs.getString("status"));
//            complaint.setSubmittedBy(rs.getString("submitted_by"));
//            complaint.setSubmittedByName(rs.getString("submitted_by_name"));
//            complaint.setAssignedTo(rs.getString("assigned_to"));
//            complaint.setAssignedToName(rs.getString("assigned_to_name"));
//            complaint.setAdminRemarks(rs.getString("admin_remarks"));
//
//            Timestamp createdAt = rs.getTimestamp("created_at");
//            if (createdAt != null) {
//                complaint.setCreatedAt(createdAt.toLocalDateTime());
//            }
//
//            Timestamp updatedAt = rs.getTimestamp("updated_at");
//            if (updatedAt != null) {
//                complaint.setUpdatedAt(updatedAt.toLocalDateTime());
//            }
//
//            return complaint;
//        }
//
//    public List<ComplaintDTO> getAllComplaints() {
//        List<ComplaintDTO> complaints = new ArrayList<>();
//
//        String sql = "SELECT c.complaint_id, c.title, c.description, c.department, c.priority, c.status, " +
//                "c.created_at, c.updated_at, c.submitted_by, c.assigned_to, c.admin_remarks, " +
//                "u.full_name AS submitted_by_name, a.full_name AS assigned_to_name " +
//                "FROM complaints c " +
//                "LEFT JOIN users u ON c.submitted_by = u.user_id " +
//                "LEFT JOIN users a ON c.assigned_to = a.user_id " +
//                "ORDER BY c.created_at DESC";
//
//        System.out.println("=== ComplaintDAO: Executing SQL ===");
//        System.out.println(sql);
//
//        try (
//                Connection conn = DatabaseConfig.getDataSource().getConnection();
//                PreparedStatement pstmt = conn.prepareStatement(sql);
//                ResultSet rs = pstmt.executeQuery()
//        ) {
//            System.out.println("Database connection successful");
//
//            int count = 0;
//            while (rs.next()) {
//                count++;
//                ComplaintDTO complaint = mapResultSetToComplaint(rs);
//                complaints.add(complaint);
//
//                System.out.println("Complaint " + count + ": " + complaint.getTitle() +
//                        " | Status: " + complaint.getStatus());
//            }
//
//            System.out.println("Total complaints fetched: " + count);
//
//        } catch (SQLException e) {
//            System.err.println("[ComplaintDAO] Error fetching all complaints: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return complaints;
//    }
//
//
//
//    public ComplaintDTO getComplaintById(String complaintId) {
//        String sql = "SELECT c.*, u.full_name as submitted_by_name, " +
//                "a.full_name as assigned_to_name FROM complaints c " +
//                "LEFT JOIN users u ON c.submitted_by = u.user_id " +
//                "LEFT JOIN users a ON c.assigned_to = a.user_id " +
//                "WHERE c.complaint_id = ?";
//
//        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, complaintId);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                return mapResultSetToComplaint(rs);
//            }
//        } catch (SQLException e) {
//            System.err.println("[ComplaintDAO] Error fetching complaint by ID: " + e.getMessage());
//        }
//        return null;
//    }
//
//
//
//    public boolean updateComplaint(ComplaintDTO complaint) {
//        String sql = "UPDATE complaints SET title = ?, description = ?, department = ?, " +
//                "priority = ? WHERE complaint_id = ? AND status = 'PENDING'";
//
//        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, complaint.getTitle());
//            pstmt.setString(2, complaint.getDescription());
//            pstmt.setString(3, complaint.getDepartment());
//            pstmt.setString(4, complaint.getPriority());
//            pstmt.setString(5, complaint.getComplaintId());
//
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("[ComplaintDAO] Error updating complaint: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean updateComplaintStatus(String complaintId, String status,
//                                         String assignedTo, String adminRemarks) {
//        String sql = "UPDATE complaints SET status = ?, assigned_to = ?, admin_remarks = ? " +
//                "WHERE complaint_id = ?";
//
//        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, status);
//            pstmt.setString(2, assignedTo);
//            pstmt.setString(3, adminRemarks);
//            pstmt.setString(4, complaintId);
//
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("[ComplaintDAO] Error updating status: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean deleteComplaint(String complaintId) {
//        String sql = "DELETE FROM complaints WHERE complaint_id = ?";
//
//        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, complaintId);
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("[ComplaintDAO] Error deleting complaint: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean deleteComplaintByEmployee(String complaintId, String userId) {
//        String sql = "DELETE FROM complaints WHERE complaint_id = ? AND submitted_by = ? AND status = 'PENDING'";
//
//        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, complaintId);
//            pstmt.setString(2, userId);
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("[ComplaintDAO] Error deleting by employee: " + e.getMessage());
//            return false;
//        }
//    }
//}


package lk.ijse.gdse72.model;

import lk.ijse.gdse72.model.podos.ComplaintDTO;
import lk.ijse.gdse72.util.DatabaseConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    public boolean createComplaint(ComplaintDTO complaint) {
        String sql = "INSERT INTO complaints (complaint_id, title, description, department, " +
                "priority, status, submitted_by, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, complaint.getComplaintId());
            pstmt.setString(2, complaint.getTitle());
            pstmt.setString(3, complaint.getDescription());
            pstmt.setString(4, complaint.getDepartment());
            pstmt.setString(5, complaint.getPriority());
            pstmt.setString(6, complaint.getStatus());
            pstmt.setString(7, complaint.getSubmittedBy());
            pstmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error creating complaint", e);
        }
    }

    public List<ComplaintDTO> getComplaintsByUser(String userId) {
        List<ComplaintDTO> complaints = new ArrayList<>();
        String sql = "SELECT c.*, u.full_name as submitted_by_name, a.full_name as assigned_to_name " +
                "FROM complaints c " +
                "LEFT JOIN users u ON c.submitted_by = u.user_id " +
                "LEFT JOIN users a ON c.assigned_to = a.user_id " +
                "WHERE c.submitted_by = ? " +
                "ORDER BY c.created_at DESC";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    complaints.add(mapResultSetToComplaint(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching complaints by user", e);
        }
        return complaints;
    }

    public List<ComplaintDTO> getAllComplaints() {
        List<ComplaintDTO> complaints = new ArrayList<>();
        String sql = "SELECT c.*, u.full_name as submitted_by_name, a.full_name as assigned_to_name " +
                "FROM complaints c " +
                "LEFT JOIN users u ON c.submitted_by = u.user_id " +
                "LEFT JOIN users a ON c.assigned_to = a.user_id " +
                "ORDER BY c.created_at DESC";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all complaints", e);
        }
        return complaints;
    }

    public ComplaintDTO getComplaintById(String complaintId) {
        String sql = "SELECT c.*, u.full_name as submitted_by_name, a.full_name as assigned_to_name " +
                "FROM complaints c " +
                "LEFT JOIN users u ON c.submitted_by = u.user_id " +
                "LEFT JOIN users a ON c.assigned_to = a.user_id " +
                "WHERE c.complaint_id = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, complaintId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToComplaint(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching complaint by ID", e);
        }
        return null;
    }

    public boolean updateComplaint(ComplaintDTO complaint) {
        String sql = "UPDATE complaints SET title = ?, description = ?, department = ?, " +
                "priority = ?, updated_at = ? WHERE complaint_id = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, complaint.getTitle());
            pstmt.setString(2, complaint.getDescription());
            pstmt.setString(3, complaint.getDepartment());
            pstmt.setString(4, complaint.getPriority());
            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(6, complaint.getComplaintId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating complaint", e);
        }
    }

    public boolean updateComplaintStatus(String complaintId, String status,
                                         String assignedTo, String adminRemarks) {
        String sql = "UPDATE complaints SET status = ?, assigned_to = ?, admin_remarks = ?, updated_at = ? WHERE complaint_id = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters to the PreparedStatement
            pstmt.setString(1, status);
            pstmt.setString(2, assignedTo);
            pstmt.setString(3, adminRemarks);
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(5, complaintId);

            // Execute update and return true if at least one row was updated
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log or handle exception as needed
            System.err.println("Error updating complaint status: " + e.getMessage());
            throw new RuntimeException("Error updating complaint status", e);
        }
    }


    public boolean deleteComplaint(String complaintId) {
        String sql = "DELETE FROM complaints WHERE complaint_id = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, complaintId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting complaint", e);
        }
    }

    public boolean deleteComplaintByEmployee(String complaintId, String userId) {
        String sql = "DELETE FROM complaints WHERE complaint_id = ? AND submitted_by = ? AND status = 'PENDING'";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, complaintId);
            pstmt.setString(2, userId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting complaint by employee", e);
        }
    }

    private ComplaintDTO mapResultSetToComplaint(ResultSet rs) throws SQLException {
        ComplaintDTO complaint = new ComplaintDTO();
        complaint.setComplaintId(rs.getString("complaint_id"));
        complaint.setTitle(rs.getString("title"));
        complaint.setDescription(rs.getString("description"));
        complaint.setDepartment(rs.getString("department"));
        complaint.setPriority(rs.getString("priority"));
        complaint.setStatus(rs.getString("status"));
        complaint.setSubmittedBy(rs.getString("submitted_by"));
        complaint.setSubmittedByName(rs.getString("submitted_by_name"));
        complaint.setAssignedTo(rs.getString("assigned_to"));
        complaint.setAssignedToName(rs.getString("assigned_to_name"));
        complaint.setAdminRemarks(rs.getString("admin_remarks"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            complaint.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            complaint.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return complaint;
    }
}
