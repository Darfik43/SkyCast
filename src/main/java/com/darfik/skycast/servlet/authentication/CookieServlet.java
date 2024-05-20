package com.darfik.skycast.servlet.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

public class CookieServlet extends HttpServlet {
    private final static int EXPIRY = 3600;
    protected Optional<Cookie> catchAuthCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(item -> item.getName().equals("sessionID")).findFirst();
    }

    protected void setSessionCookie(HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie("sessionID", req.getSession().getId());
        cookie.setPath("/");
        cookie.setMaxAge(EXPIRY);
        resp.addCookie(cookie);
    }
}
