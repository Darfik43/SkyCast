package com.darfik.skycast.factory;

import com.darfik.skycast.dao.UserDAO;
import com.darfik.skycast.dao.UserSessionDAO;
import com.darfik.skycast.service.PasswordEncryptor;
import com.darfik.skycast.service.UserService;
import com.darfik.skycast.service.UserSessionService;

public class UserServiceFactory {

    public static UserService createUserService() {
        UserDAO userDAO = UserDAO.getInstance();
        UserSessionDAO userSessionDAO = UserSessionDAO.getInstance();
        UserSessionService userSessionService = new UserSessionService(userSessionDAO);
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
        return new UserService(userDAO, userSessionService, passwordEncryptor);
    }
}
