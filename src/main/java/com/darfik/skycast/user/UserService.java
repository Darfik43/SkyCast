package com.darfik.skycast.user;

import com.darfik.skycast.usersession.UserSessionDTO;
import com.darfik.skycast.usersession.UserSessionService;
import com.darfik.skycast.usersession.UserSessionServiceFactory;
import com.darfik.skycast.usersession.UserSessionServiceFactoryImp;
import com.darfik.skycast.utils.PasswordEncryptor;

public class UserService {
    private static UserService userService;
    private final UserDAO userDAO = UserDAO.getInstance();
    private final UserSessionServiceFactory userSessionServiceFactory = new UserSessionServiceFactoryImp();
    private final PasswordEncryptor passwordEncryptor = new PasswordEncryptor();


    public void registerUser(UserRegistrationDTO userDTO, UserSessionDTO userSessionDTO) {
        String hashedPassword = passwordEncryptor.encryptPassword(userDTO.password());
        UserSessionService userSessionService = userSessionServiceFactory.build();

        User newUser = UserMapper.toModel(userDTO);
        newUser.setPassword(hashedPassword);

        userDAO.save(newUser);

        userSessionService.createAndSaveUserSession(newUser, userSessionDTO);
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
}
