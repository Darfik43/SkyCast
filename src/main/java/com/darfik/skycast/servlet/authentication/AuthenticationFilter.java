package com.darfik.skycast.servlet.authentication;

import com.darfik.skycast.dao.UserSessionDAO;
import com.darfik.skycast.model.UserSession;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
public class AuthenticationFilter extends CookieServlet implements Filter {
     private final UserSessionDAO userSessionDAO = UserSessionDAO.getInstance();

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        Optional<Cookie> authCookie = catchAuthCookie(request);
        boolean isLoggedIn = authCookie.isPresent()
                && !userSessionDAO.isExpired(new UserSession(authCookie.toString()));

        request.setAttribute("isLoggedIn", isLoggedIn);
        chain.doFilter(request, response);
    }
}

