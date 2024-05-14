package com.darfik.skycast.usersession;
import com.darfik.skycast.user.User;

import java.time.LocalDateTime;

public class UserSessionService {
    private final UserSessionDAO userSessionDAO = UserSessionDAO.getInstance();

    public void createAndSaveUserSession(User user, UserSessionDTO userSessionDTO) {
        userSessionDAO.deleteExpiredSessions(user);

        UserSession userSession = UserSessionMapper.toModel(userSessionDTO);

        userSession.setId(userSessionDTO.id());
        userSession.setUser(user);
        userSession.setExpiresAt(LocalDateTime.now().plusHours(1));

        userSessionDAO.update(userSession);
    }

    public void logout(UserSessionDTO userSessionDTO) {
        userSessionDAO.delete(userSessionDTO.id());
    }
}
