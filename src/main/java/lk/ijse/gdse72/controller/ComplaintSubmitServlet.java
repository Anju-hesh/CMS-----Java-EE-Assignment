package lk.ijse.gdse72.controller;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import lk.ijse.gdse72.model.ComplaintDAO;
import lk.ijse.gdse72.model.podos.ComplaintDTO;
import lk.ijse.gdse72.util.IdGenerator;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/employee/submit-complaint")
public class ComplaintSubmitServlet extends HttpServlet {

    private ComplaintDAO complaintDAO = new ComplaintDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in doGet Method of Complain Submit ...");

        req.getRequestDispatcher("/view/complaint-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in doPost Method of Complain Submit ...");

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String department = req.getParameter("department");
        String priority = req.getParameter("priority");

        HttpSession session = req.getSession();
        String submittedBy = ((lk.ijse.gdse72.model.podos.UserDTO)session.getAttribute("user")).getUserId();

        ComplaintDTO complaint = new ComplaintDTO();
        complaint.setComplaintId(IdGenerator.generateComplaintId());
        complaint.setTitle(title);
        complaint.setDescription(description);
        complaint.setDepartment(department);
        complaint.setPriority(priority);
        complaint.setStatus("PENDING");
        complaint.setSubmittedBy(submittedBy);
        complaint.setCreatedAt(LocalDateTime.now());

        System.out.println("before call complainDAO.createComplaint Method ...");
        boolean saved = complaintDAO.createComplaint(complaint);
        System.out.println("After call complaintDAO.createComplaint Method ...");

        if (saved) {
            System.out.println("saved Complaint ...");
            resp.sendRedirect(req.getContextPath() + "/view/my-complaints.jsp?success=1");
        } else {
            System.out.println(" Save Failed Complaint ...");
            req.setAttribute("error", "Failed to submit complaint.");
            req.getRequestDispatcher("/view/complaint-form.jsp").forward(req, resp);
        }
    }
}