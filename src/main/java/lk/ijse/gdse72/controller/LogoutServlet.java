package lk.ijse.gdse72.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        System.out.println("Redirecting to login page: " + req.getContextPath() + "/index.jsp");
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}