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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserServiceFactory userServiceFactory = new UserServiceFactoryImp();
    private final UserService userService = userServiceFactory.build();
    private final UserSessionServiceFactory userSessionServiceFactory = new UserSessionServiceFactoryImp();
    private final UserSessionService userSessionService = userSessionServiceFactory.build();

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
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserWithPasswordDTO userWithPasswordDTO = new UserWithPasswordDTO(username, password);
        UserSessionDTO userSessionDTO = new UserSessionDTO(req.getSession().getId());

        userService.authenticateUser(userWithPasswordDTO, userSessionDTO);

        String newUserId = String.valueOf(userService.getNewUserId());
        Cookie userIdCookie = new Cookie("userId", newUserId);
        resp.addCookie(userIdCookie);

        resp.getWriter().print("You've logged in");

    }
}
