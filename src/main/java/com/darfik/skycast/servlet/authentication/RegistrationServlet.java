package com.darfik.skycast.servlet.authentication;

import com.darfik.skycast.exception.DatabaseException;
import com.darfik.skycast.exception.UserAlreadyExistsException;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.service.UserService;
import com.darfik.skycast.servlet.RenderServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/register")
public class RegistrationServlet extends RenderServlet {
    private final UserService userService;

    public RegistrationServlet() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processTemplate("register", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDTO userDTO = new UserDTO(
                    req.getParameter("username").trim(),
                    req.getParameter("password")
            );
            userService.registerUser(userDTO);
            req.getRequestDispatcher("/login").forward(req, resp);
        } catch (UserAlreadyExistsException e) {
            req.setAttribute("errorMessage", "User already exists");
            super.processTemplate("register", req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "A database error occurred. Please try again later.");
            super.processTemplate("register", req, resp);
        }
    }
}
