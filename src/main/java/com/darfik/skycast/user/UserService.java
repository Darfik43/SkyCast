package com.darfik.skycast.user;

import com.darfik.skycast.usersession.UserSessionService;
import com.darfik.skycast.utils.PasswordEncryptor;

public class UserService {
    private static UserService userService;
    private final UserDAO userDAO = UserDAO.getInstance();
    private final UserSessionService userSessionService = UserSessionService.getInstance();
    private final PasswordEncryptor passwordEncryptor = new PasswordEncryptor();


    public void registerUser(UserRegistrationDTO userDTO) {
        String hashedPassword = passwordEncryptor.encryptPassword(userDTO.password());

        User newUser = UserMapper.toModel(userDTO);
        newUser.setPassword(hashedPassword);

        userDAO.save(newUser);

        userSessionService.createAndSaveUserSession(newUser);
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
}
