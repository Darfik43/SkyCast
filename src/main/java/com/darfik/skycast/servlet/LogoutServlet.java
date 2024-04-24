package com.darfik.skycast.servlet;

import com.darfik.skycast.user.UserService;
import com.darfik.skycast.user.UserServiceFactory;
import com.darfik.skycast.usersession.UserSessionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends RenderServlet {
    private final UserService userService = UserServiceFactory.build();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserSessionDTO userSessionDTO = new UserSessionDTO(req.getSession().getId());
            userService.logout(userSessionDTO);
            session.invalidate();
            Cookie cookie = new Cookie("sessionID", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            resp.addCookie(cookie);
            resp.getWriter().print("You've logged out");
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
