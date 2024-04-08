package com.darfik.skycast.dtos;

import com.darfik.skycast.models.User;

public class UserMapper {
    public UserDTO toDto(User user) {
        String login = user.getLogin();

        return new UserDTO(login);
    }
}
