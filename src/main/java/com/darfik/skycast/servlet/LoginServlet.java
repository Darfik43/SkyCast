package com.darfik.skycast.servlet;

import com.darfik.skycast.user.UserDTO;
import com.darfik.skycast.user.UserService;
import com.darfik.skycast.user.UserServiceFactory;
import com.darfik.skycast.usersession.UserSessionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.rmi.NoSuchObjectException;

@WebServlet("/login")
public class LoginServlet extends RenderServlet {
    private final UserService userService = UserServiceFactory.build();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ((Boolean) req.getAttribute("isLoggedIn")) {
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.getRequestDispatcher("/templates/login.html").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userService.authorizeUser(
                    new UserDTO(req.getParameter("username"), req.getParameter("password")),
                    new UserSessionDTO(req.getSession().getId()));
            req.getSession().setAttribute("username", req.getParameter("username"));
            Cookie cookie = new Cookie("sessionID", req.getSession().getId());
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            resp.addCookie(cookie);
            resp.getWriter().print("You've logged in");
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (NoSuchObjectException e) {
            resp.getWriter().print("Incorrect username or password");
        }
    }
}
