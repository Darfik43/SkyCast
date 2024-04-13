package com.darfik.skycast.usersession;

import com.darfik.skycast.user.User;
import java.time.LocalDateTime;

public class UserSessionService {
    private final UserSessionDAO userSessionDAO = UserSessionDAO.getInstance();

    public void createAndSaveUserSession(User user, UserSessionDTO userSessionDTO) {
        UserSession userSession = UserSessionMapper.toModel(userSessionDTO);

        userSession.setId(userSessionDTO.id());
        userSession.setUser(user);
        userSession.setExpiresAt(LocalDateTime.now().plusHours(1));

        userSessionDAO.save(userSession);
    }

    public void logout(UserSessionDTO userSessionDTO) {
        userSessionDAO.delete(userSessionDTO.id());
    }

    public void deleteExpiredSessions() {
        userSessionDAO.deleteExpiredSessions();
    }
}
