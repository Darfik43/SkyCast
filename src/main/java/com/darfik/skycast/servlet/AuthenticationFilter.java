package com.darfik.skycast.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
public class AuthenticationFilter extends CookieServlet implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if ("/register".equals(requestURI) || "/logout".equals(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        boolean isLoggedIn = false;
        Optional<Cookie> authCookie = catchAuthCookie(request);
        if (authCookie.isPresent()) {
            isLoggedIn = true;
        }

        request.setAttribute("isLoggedIn", isLoggedIn);
        chain.doFilter(request, response);
    }
}

