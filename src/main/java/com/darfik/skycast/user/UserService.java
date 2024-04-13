package com.darfik.skycast.user;

import com.darfik.skycast.usersession.*;
import com.darfik.skycast.utils.PasswordEncryptor;

public class UserService {
    private User newUser;
    private final UserDAO userDAO;
    private final UserSessionServiceFactory userSessionServiceFactory;
    private final UserSessionService userSessionService;
    private final PasswordEncryptor passwordEncryptor;

    UserService() {
        userDAO = UserDAO.getInstance();
        userSessionServiceFactory = new UserSessionServiceFactoryImp();
        userSessionService = userSessionServiceFactory.build();
        passwordEncryptor = new PasswordEncryptor();
    }


    public void registerUser(UserWithPasswordDTO userDTO) {
        newUser = UserMapper.toModel(userDTO);
        newUser.setPassword(passwordEncryptor.encryptPassword(userDTO.password()));
        userDAO.save(newUser);
    }

    public void authenticateUser(UserWithPasswordDTO userWithPasswordDTO, UserSessionDTO userSessionDTO) {
        if (isAuthenticated(userWithPasswordDTO)) {
            newUser = UserMapper.toModel(userWithPasswordDTO);
            setNewUser();
            userSessionService.createAndSaveUserSession(newUser, userSessionDTO);
        }

        setNewUser();
    }

    private boolean isAuthenticated(UserWithPasswordDTO userWithPasswordDTO) {
        return userDAO.getByName(userWithPasswordDTO.username())
                .map(user -> passwordEncryptor.verifyPassword(userWithPasswordDTO.password(), user.getPassword()))
                .orElse(false);
    }

    public void logout(UserSessionDTO userSessionDTO) {
        if (userSessionDTO != null) {
            userSessionService.logout(userSessionDTO);
        }
    }

    public String getNewUserId() {
        return newUser.getId().toString();
    }

    private void setNewUser() {
        userDAO.getByName(newUser.getUsername()).ifPresent(user -> newUser = user);
    }
}
