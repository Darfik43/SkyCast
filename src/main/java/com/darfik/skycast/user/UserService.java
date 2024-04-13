package com.darfik.skycast.user;

import com.darfik.skycast.usersession.*;
import com.darfik.skycast.utils.PasswordEncryptor;

import java.util.NoSuchElementException;

public class UserService {
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
        User user = UserMapper.toModel(userDTO);
        user.setPassword(passwordEncryptor.encryptPassword(userDTO.getPassword()));
        userDAO.save(user);
    }

    public String authorizeUser(UserDTO userDTO, UserSessionDTO userSessionDTO) {
        if (userExistsAndPasswordCorrect(userDTO)) {
            User user = userDAO.getByName(userDTO.getUsername()).get();
            userSessionService.createAndSaveUserSession(user, userSessionDTO);
            return user.getId().toString();
        } else {
            throw new NoSuchElementException();
        }
    }

    private boolean userExistsAndPasswordCorrect(UserDTO userDTO) {
        return userDAO.getByName(userDTO.getUsername())
                .filter(user -> passwordEncryptor.verifyPassword(userDTO.getPassword(), user.getPassword()))
                .isPresent();
    }

    public void logout(UserSessionDTO userSessionDTO) {
        if (userSessionDTO != null) {
            userSessionService.logout(userSessionDTO);
        }
    }
}
