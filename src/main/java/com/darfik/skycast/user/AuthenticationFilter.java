package com.darfik.skycast.user;

import com.darfik.skycast.servlet.BaseServlet;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
public class AuthenticationFilter extends BaseServlet implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (("/".equals(requestURI) || "/login".equals(requestURI)) || "/register".equals(requestURI) || "/logout".equals(requestURI)) {
            chain.doFilter(request, response);
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

