package com.darfik.skycast.user;

import com.darfik.skycast.usersession.UserSessionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.rmi.NoSuchObjectException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserServiceFactory userServiceFactory = new UserServiceFactoryImp();
    private final UserService userService = userServiceFactory.build();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userService.authorizeUser(
                    new UserDTO(req.getParameter("username"), req.getParameter("password")),
                    new UserSessionDTO(req.getSession().getId()));
            req.getSession().setAttribute("username", req.getParameter("username"));
            resp.getWriter().print("You've logged in");
        } catch (NoSuchObjectException e) {
            resp.getWriter().print("Incorrect username or password");
        }
    }
}
