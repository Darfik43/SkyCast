package com.darfik.skycast.user;

import com.darfik.skycast.usersession.UserSessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/register")
public class UserRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = UserService.getInstance();
        HttpSession session = req.getSession(true);

        String password = req.getParameter("password");
        String username = req.getParameter("username");

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(username, password);
        userService.registerUser(userRegistrationDTO, session);


        resp.getWriter().print("You've created your account");
    }
}
