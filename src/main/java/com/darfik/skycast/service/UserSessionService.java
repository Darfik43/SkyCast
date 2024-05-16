package com.darfik.skycast.service;
import com.darfik.skycast.dao.UserSessionDAO;
import com.darfik.skycast.model.User;
import com.darfik.skycast.model.UserSession;
import com.darfik.skycast.model.dto.UserSessionDTO;

import java.time.LocalDateTime;

public class UserSessionService {
    private final UserSessionDAO userSessionDAO;

    public UserSessionService() {
        this.userSessionDAO = UserSessionDAO.getInstance();
    }

    public void updateUserSessions(User user, UserSessionDTO userSessionDTO) {
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
