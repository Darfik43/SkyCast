package com.darfik.skycast.servlet;

import com.darfik.skycast.user.UserDTO;
import com.darfik.skycast.user.UserService;
import com.darfik.skycast.user.UserServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.HibernateException;

import java.io.IOException;


@WebServlet("/register")
public class UserRegistrationServlet extends RenderServlet {
    private final UserService userService;

    public UserRegistrationServlet() {
        userService = UserServiceFactory.build();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/templates/register.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            UserDTO userDTO = new UserDTO(
                    req.getParameter("username"),
                    req.getParameter("password")
            );
            userService.registerUser(userDTO);
            req.getRequestDispatcher("/login").forward(req, resp);
            resp.getWriter().print("You've created your account");
        } catch (HibernateException e) {
            resp.getWriter().print(e.getMessage());
        }

    }
}
