package com.darfik.skycast.user;

public class UserMapper {
    public static UserDTO toDto(User user) {
        return new UserDTO(user.getUsername());
    }

    public static User toModel(UserWithPasswordDTO userDTO) {
        return new User(userDTO.username(), userDTO.password());
    }
}
