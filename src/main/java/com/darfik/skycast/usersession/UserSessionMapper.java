package com.darfik.skycast.usersession;


public class UserSessionMapper {
    public static UserSessionDTO toDto(UserSession userSession) {
        return new UserSessionDTO(userSession.getId());
    }

    public static UserSession toModel(UserSessionDTO userSessionDTO) {
        return new UserSession(userSessionDTO.id());
    }
}
