package com.darfik.skycast.servlet.authentication;

import com.darfik.skycast.enums.SkycastURL;
import com.darfik.skycast.exception.InvalidCredentialsException;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.model.dto.UserSessionDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends BaseAuthenticationServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ((Boolean) req.getAttribute("isLoggedIn")) {
            resp.sendRedirect(req.getContextPath() + SkycastURL.HOME_URL.getValue());
        } else {
            processTemplate("login", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            userService.authorizeUser(
                    new UserDTO(req.getParameter("username").trim(), req.getParameter("password")),
                    new UserSessionDTO(req.getSession().getId()));
            req.getSession().setAttribute("username", req.getParameter("username").trim());

            CookieServlet cookieServlet = new CookieServlet();
            cookieServlet.setSessionCookie(req, resp);

            resp.sendRedirect(req.getContextPath() + SkycastURL.HOME_URL.getValue());
        }  catch (InvalidCredentialsException e) {
            req.setAttribute("errorMessage", "Incorrect username or password");
            super.processTemplate("login", req, resp);
        }
    }
}
