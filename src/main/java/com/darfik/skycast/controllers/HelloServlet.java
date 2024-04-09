package com.darfik.skycast.controllers;

import java.io.*;

import com.darfik.skycast.daos.UserDAO;
import com.darfik.skycast.models.User;
import com.darfik.skycast.utils.HibernateUtil;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.hibernate.Session;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {


    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        User newUser = new User();
        newUser.setLogin("Test");
        newUser.setPassword("Test");


        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}