package com.darfik.skycast.user;

public class UserMapper {
    public UserDTO toDto(User user) {
        return new UserDTO(user.getUsername());
    }

    public User toModel(UserRegistrationDTO userDTO) {
        return new User(userDTO.username(), userDTO.password());
    }
}
