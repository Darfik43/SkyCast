package com.darfik.skycast.mapper;

import com.darfik.skycast.model.User;
import com.darfik.skycast.model.dto.UserDTO;

public class UserMapper {

    public static User toModel(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword());
    }
}
