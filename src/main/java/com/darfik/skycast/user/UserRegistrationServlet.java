package com.darfik.skycast.user;

import com.darfik.skycast.usersession.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;

import java.io.IOException;

@WebServlet("/register")
public class UserRegistrationServlet extends HttpServlet {
    private final UserService userService;

    private UserRegistrationServlet() {
        UserServiceFactory userServiceFactory = new UserServiceFactoryImp();
        userService = userServiceFactory.build();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        UserWithPasswordDTO userWithPasswordDTO = new UserWithPasswordDTO(
                req.getParameter("username"),
                req.getParameter("password")
        );

        userService.registerUser(userWithPasswordDTO);

        resp.getWriter().print("You've created your account");
    }
}
