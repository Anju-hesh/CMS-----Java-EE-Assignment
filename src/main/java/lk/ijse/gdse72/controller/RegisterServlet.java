//package lk.ijse.gdse72.controller;
//
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.WebServlet;
//import lk.ijse.gdse72.model.UserDAO;
//import lk.ijse.gdse72.model.podos.UserDTO;
//import lk.ijse.gdse72.util.IdGenerator;
//import lk.ijse.gdse72.util.ValidationUtil;
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//@WebServlet("/registerservlet")
//public class RegisterServlet extends HttpServlet {
//
//    private UserDAO userDAO = new UserDAO();
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("In Get Method");
//        req.getRequestDispatcher("/view/register.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("In Post Method");
//
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        String fullName = req.getParameter("fullName");
//        int number = Integer.parseInt(req.getParameter("number"));
//        String email = req.getParameter("email");
//        String role = req.getParameter("role");
//
//        // Basic validation
//        if (!ValidationUtil.isValidString(username) || !ValidationUtil.isValidString(password) ||
//                !ValidationUtil.isValidString(fullName) || !ValidationUtil.isValidEmail(email) ||
//                !(role.equals("EMPLOYEE") || role.equals("ADMIN"))) {
//            req.setAttribute("error", "Invalid input data");
//            req.getRequestDispatcher("/view/register.jsp").forward(req, resp);
//            System.out.println("invalid ekak mokak hari athi");
//            return;
//        }
//
//        // Create UserDTO
//        UserDTO user = new UserDTO(
//                IdGenerator.generateUserId(),
//                username,
//                password,
//                fullName,
//                email,
//                number,
//                role,
//                LocalDateTime.now()
//        );
//
//        System.out.println("Before call dao");
//        boolean saved = userDAO.createUser(user);
//        System.out.println("After Call Dao");
//
//        if (saved) {
//            System.out.println("saved user");
//            resp.sendRedirect(req.getContextPath() + "/index.jsp?register=success");
//        } else {
//            System.out.println("Some Thing Went Wrong");
//            req.setAttribute("error", "User registration failed, username might be taken.");
//            req.getRequestDispatcher("/view/register.jsp").forward(req, resp);
//        }
//    }
//}


package lk.ijse.gdse72.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import lk.ijse.gdse72.model.UserDAO;
import lk.ijse.gdse72.model.podos.UserDTO;
import lk.ijse.gdse72.util.IdGenerator;
import lk.ijse.gdse72.util.ValidationUtil;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/registerservlet")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("number");
        String email = req.getParameter("email");
        String role = req.getParameter("role");

        boolean hasError = false;

        if (!ValidationUtil.isValidUsername(username)) {
            req.setAttribute("usernameError", "Invalid username.");
            hasError = true;
        }
        if (!ValidationUtil.isValidPassword(password)) {
            req.setAttribute("passwordError", "Password must meet criteria.");
            hasError = true;
        }
        if (!ValidationUtil.isValidString(fullName)) {
            req.setAttribute("fullNameError", "Full name is required.");
            hasError = true;
        }
        if (!ValidationUtil.isValidPhone(phone)) {
            req.setAttribute("phoneError", "Invalid phone number.");
            hasError = true;
        }
        if (!ValidationUtil.isValidEmail(email)) {
            req.setAttribute("emailError", "Invalid email address.");
            hasError = true;
        }
        if (!(role != null && (role.equalsIgnoreCase("EMPLOYEE") || role.equalsIgnoreCase("ADMIN")))) {
            req.setAttribute("roleError", "Role must be EMPLOYEE or ADMIN.");
            hasError = true;
        }


        req.setAttribute("oldUsername", username);
        req.setAttribute("oldFullName", fullName);
        req.setAttribute("oldPhone", phone);
        req.setAttribute("oldEmail", email);
        req.setAttribute("oldRole", role);

        if (hasError) {
            System.out.println("Validation failed ... forwarding back to form");
            req.getRequestDispatcher("/view/register.jsp").forward(req, resp);
            return;
        }


        UserDTO user = new UserDTO(
                IdGenerator.generateUserId(),
                username,
                password,
                fullName,
                email,
                phone,
                role,
                LocalDateTime.now()
        );

        System.out.println("Before call dao");
        boolean saved = userDAO.createUser(user);
        System.out.println("After Call dao");

        if (saved) {
            System.out.println("saved user");
            resp.sendRedirect(req.getContextPath() + "/index.jsp?register=success");
        } else {
            System.out.println("Some Thing Went Wrong");
            req.setAttribute("error", "User registration failed. Username might already exist.");
            req.getRequestDispatcher("/view/register.jsp").forward(req, resp);
        }
    }
}
