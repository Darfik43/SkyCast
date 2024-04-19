package com.darfik.skycast.user;

import com.darfik.skycast.usersession.*;
import com.darfik.skycast.utils.PasswordEncryptor;

import java.util.NoSuchElementException;

public class UserService {
    private User user;
    private final UserDAO userDAO;
    private final UserSessionService userSessionService;
    private final PasswordEncryptor passwordEncryptor;

    UserService() {
        userDAO = UserDAO.getInstance();
        UserSessionServiceFactory userSessionServiceFactory = new UserSessionServiceFactoryImp();
        userSessionService = userSessionServiceFactory.build();
        passwordEncryptor = new PasswordEncryptor();
    }


    public void registerUser(UserDTO userDTO) {
        if (!userExists(userDTO.getUsername())) {
            User user = UserMapper.toModel(userDTO);
            user.setPassword(passwordEncryptor.encryptPassword(userDTO.getPassword()));
            userDAO.save(user);
        }
    }

    public void authorizeUser(UserDTO userDTO, UserSessionDTO userSessionDTO) {
        if (userExists(userDTO.getUsername()) && isPasswordCorrect(userDTO)) {
            User user = userDAO.find(userDTO.getUsername()).get();
            userSessionService.createAndSaveUserSession(user, userSessionDTO);
        } else {
            throw new NoSuchElementException();
        }
    }

    private boolean isPasswordCorrect(UserDTO userDTO) {
        return userDAO.find(userDTO.getUsername())
                .map(user -> passwordEncryptor.verifyPassword(userDTO.getPassword(), user.getPassword())).orElse(false);
    }

    private boolean userExists(String username) {
        return userDAO.find(username).isPresent();
    }

    public void logout(UserSessionDTO userSessionDTO) {
        if (userSessionDTO != null) {
            userSessionService.logout(userSessionDTO);
        }
    }
}
