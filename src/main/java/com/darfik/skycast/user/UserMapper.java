package com.darfik.skycast.user;

public class UserMapper {
    public static UserDTO toDto(User user) {
        return new UserDTO(user.getId());
    }

    public static User toModel(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword());
    }
}
