package com.darfik.skycast.usersession;

import com.darfik.skycast.user.User;
import com.darfik.skycast.user.UserRegistrationDTO;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserSessionService {
    private static UserSessionService userSessionService;
    private final UserSessionDAO userSessionDAO = UserSessionDAO.getInstance();

    public void createAndSaveUserSession(User user, HttpSession session) {
        UserSession userSession = new UserSession();

        userSession.setId(session.getId());
        userSession.setUser(user);
        userSession.setExpiresAt(LocalDateTime.now().plusHours(1));

        userSessionDAO.save(userSession);

    }

    public void deleteExpiredSessions() {
        userSessionDAO.deleteExpiredSessions();
    }

    public static UserSessionService getInstance() {
        if (userSessionService == null) {
            userSessionService = new UserSessionService();
        }
        return userSessionService;
    }
}
