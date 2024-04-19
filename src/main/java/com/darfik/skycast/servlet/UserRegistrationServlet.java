package com.darfik.skycast.servlet;

import com.darfik.skycast.user.UserDTO;
import com.darfik.skycast.user.UserService;
import com.darfik.skycast.user.UserServiceFactory;
import com.darfik.skycast.user.UserServiceFactoryImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class UserRegistrationServlet extends BaseServlet {
    private final UserService userService;

    public UserRegistrationServlet() {
        UserServiceFactory userServiceFactory = new UserServiceFactoryImp();
        userService = userServiceFactory.build();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = new UserDTO(
                req.getParameter("username"),
                req.getParameter("password")
        );

        userService.registerUser(userDTO);
        resp.getWriter().print("You've created your account");
    }
}
