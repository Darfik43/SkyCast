package com.darfik.skycast.user;

import com.darfik.skycast.utils.PasswordEncryptor;

public class UserService {
    private final UserDAO userDAO = UserDAO.getInstance();
    private final PasswordEncryptor passwordEncryptor = new PasswordEncryptor();

    public void registerUser(UserRegistrationDTO userDTO) {
        String hashedPassword = passwordEncryptor.encryptPassword(userDTO.password());

        User newUser = UserMapper.toModel(userDTO);
        newUser.setPassword(hashedPassword);

        userDAO.save(newUser);
    }
}
