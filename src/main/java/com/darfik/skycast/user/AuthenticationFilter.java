package com.darfik.skycast.user;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        Cookie[] cookies = request.getCookies();

        if (("/".equals(requestURI) || "/login".equals(requestURI)) || "/register".equals(requestURI) || "/logout".equals(requestURI)) {
            chain.doFilter(request, response);
        }

        Cookie sessionCookie = Arrays.stream(cookies)
                .filter((cookie) -> Objects.equals(cookie.getName(), "sessionID"))
                .findFirst().orElse(null);
        if (sessionCookie != null && sessionCookie.getName().equals("sessionID")) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

}

