package com.darfik.skycast.user;

import com.darfik.skycast.user.User;
import com.darfik.skycast.user.UserDTO;

public class UserMapper {
    public UserDTO toDto(User user) {
        String login = user.getLogin();

        return new UserDTO(login);
    }
}
