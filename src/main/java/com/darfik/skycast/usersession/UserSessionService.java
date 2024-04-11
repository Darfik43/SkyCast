package com.darfik.skycast.usersession;

import com.darfik.skycast.user.User;
import com.darfik.skycast.user.UserRegistrationDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserSessionService {
    private static UserSessionService userSessionService;
    private final UserSessionDAO userSessionDAO = UserSessionDAO.getInstance();

    public void createAndSaveUserSession(User user) {
        UserSession userSession = new UserSession();

        userSession.setId(generateGUID());
        userSession.setUser(user);
        userSession.setExpiresAt(LocalDateTime.now().plusHours(1));

        userSessionDAO.save(userSession);

    }

    private String generateGUID() {
        return UUID.randomUUID().toString();
    }

    public static UserSessionService getInstance() {
        if (userSessionService == null) {
            userSessionService = new UserSessionService();
        }
        return userSessionService;
    }
}
