package com.darfik.skycast.servlet.authentication;

import com.darfik.skycast.factory.UserServiceFactory;
import com.darfik.skycast.service.UserService;
import com.darfik.skycast.servlet.RenderServlet;

public class BaseAuthenticationServlet extends RenderServlet {

    protected UserService userService;

    @Override
    public void init() {
        userService = UserServiceFactory.createUserService();
    }
}
