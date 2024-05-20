package com.darfik.skycast.servlet.authentication;

import com.darfik.skycast.SkycastURL;
import com.darfik.skycast.dao.UserDAO;
import com.darfik.skycast.exception.DatabaseException;
import com.darfik.skycast.exception.UserAlreadyExistsException;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.service.PasswordEncryptor;
import com.darfik.skycast.service.UserService;
import com.darfik.skycast.service.UserSessionService;
import com.darfik.skycast.servlet.RenderServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/register")
public class RegistrationServlet extends BaseAuthenticationServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processTemplate("register", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getParameter("password").equals(req.getParameter("confirmPassword"))) {
            req.setAttribute("errorMessage", "Please make sure your passwords match");
            super.processTemplate("register", req, resp);
        }

        try {
            UserDTO userDTO = new UserDTO(
                    req.getParameter("username").trim(),
                    req.getParameter("password")
            );
            userService.registerUser(userDTO);
            req.getRequestDispatcher(SkycastURL.LOGIN_URL.getValue()).forward(req, resp);
        } catch (UserAlreadyExistsException e) {
            req.setAttribute("errorMessage", "User already exists");
            super.processTemplate("register", req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "A database error occurred. Please try again later.");
            super.processTemplate("register", req, resp);
        }
    }
}
