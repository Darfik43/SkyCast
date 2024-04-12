package com.darfik.skycast.user;

import com.darfik.skycast.usersession.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class UserRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(true).getId();
        UserService userService = UserService.getInstance();
        UserSessionDTO userSessionDTO = new UserSessionDTO(sessionId);

        String password = req.getParameter("password");
        String username = req.getParameter("username");

        UserWithPasswordDTO userWithPasswordDTO = new UserWithPasswordDTO(username, password);
        userService.registerUser(userWithPasswordDTO, userSessionDTO);

        resp.getWriter().print("You've created your account");
    }
}
