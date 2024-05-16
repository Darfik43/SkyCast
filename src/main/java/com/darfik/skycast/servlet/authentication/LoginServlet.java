package com.darfik.skycast.servlet.authentication;

import com.darfik.skycast.exception.InvalidCredentialsException;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.service.UserService;
import com.darfik.skycast.model.dto.UserSessionDTO;
import com.darfik.skycast.servlet.RenderServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends RenderServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ((Boolean) req.getAttribute("isLoggedIn")) {
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.getRequestDispatcher("/templates/login.html").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            userService.authorizeUser(
                    new UserDTO(req.getParameter("username").trim(), req.getParameter("password")),
                    new UserSessionDTO(req.getSession().getId()));
            req.getSession().setAttribute("username", req.getParameter("username").trim());
            Cookie cookie = new Cookie("sessionID", req.getSession().getId());
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            resp.addCookie(cookie);
            resp.getWriter().print("You've logged in");
            resp.sendRedirect(req.getContextPath() + "/home");
        }  catch (InvalidCredentialsException e) {
            req.setAttribute("errorMessage", "Incorrect username or password");
            super.processTemplate("error", req, resp);
        }
    }
}
