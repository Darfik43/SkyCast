package com.darfik.skycast.user;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());

        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();

        if (session == null && cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    session = request.getSession();
                    break;
                }
            }
        }
        if (("/".equals(requestURI) || "/login".equals(requestURI)) || "/register".equals(requestURI)) {
            chain.doFilter(request, response);
        } else {
            if (session != null && session.getAttribute("username") != null) {
                chain.doFilter(req, resp);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }
    }
}
