package com.darfik.skycast.user;

import com.darfik.skycast.usersession.UserSessionDTO;
import com.darfik.skycast.usersession.UserSessionService;
import com.darfik.skycast.usersession.UserSessionServiceFactory;
import com.darfik.skycast.usersession.UserSessionServiceFactoryImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {

                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userId = userService.authorizeUser(
                    new UserDTO(req.getParameter("username"), req.getParameter("password")),
                    new UserSessionDTO(req.getSession().getId()));
            resp.addCookie(new Cookie("userId", userId));
            resp.getWriter().print("You've logged in");
        } catch (NoSuchObjectException e) {
            resp.getWriter().print("Incorrect username or password");
        }
    }
}
