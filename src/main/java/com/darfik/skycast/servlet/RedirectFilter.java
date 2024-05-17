package com.darfik.skycast.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class RedirectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());

        if ("/".equals(requestURI)) {
            ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/home");
            return;
        }

        if ("/search".equals(requestURI) && !((Boolean) req.getAttribute("isLoggedIn"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendRedirect(((HttpServletRequest) req).getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}