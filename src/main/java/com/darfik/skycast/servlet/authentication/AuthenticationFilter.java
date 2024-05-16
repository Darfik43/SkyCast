package com.darfik.skycast.servlet.authentication;

import com.darfik.skycast.model.UserSession;
import com.darfik.skycast.dao.UserSessionDAO;
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
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());

        if ("/".equals(requestURI)) {
            ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/home");
            return;
        }

        if ("/register".equals(requestURI) || "/logout".equals(requestURI)) {
            chain.doFilter(request, response);
            return;
        }



        boolean isLoggedIn = false;
        Optional<Cookie> authCookie = catchAuthCookie(request);
        if (authCookie.isPresent()
            && !userSessionDAO.isExpired(new UserSession(authCookie.toString()))) {
            isLoggedIn = true;
        }

        if ("/search".equals(requestURI) && !isLoggedIn) {
            req.getRequestDispatcher("/templates/login.html").forward(req, resp);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        request.setAttribute("isLoggedIn", isLoggedIn);
        chain.doFilter(request, response);
    }
}

