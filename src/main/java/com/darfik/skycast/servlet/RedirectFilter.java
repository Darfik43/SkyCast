package com.darfik.skycast.servlet;

import com.darfik.skycast.enums.SkycastURL;
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
            ((HttpServletResponse) resp).sendRedirect(
                    ((HttpServletRequest) req).getContextPath()
                            + SkycastURL.HOME_URL.getValue());

        } else if (SkycastURL.SEARCH_URL.getValue().equals(requestURI) && !((Boolean) req.getAttribute("isLoggedIn"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendRedirect(
                    ((HttpServletRequest) req).getContextPath()
                            + SkycastURL.LOGIN_URL.getValue());
        } else {
            chain.doFilter(request, response);
        }
    }
}
