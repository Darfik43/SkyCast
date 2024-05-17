package com.darfik.skycast.service;


import com.darfik.skycast.model.UserSession;
import com.darfik.skycast.model.dto.UserSessionDTO;

public class UserSessionMapper {

    public static UserSession toModel(UserSessionDTO userSessionDTO) {
        return new UserSession(userSessionDTO.id());
    }
}
