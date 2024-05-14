package com.darfik.skycast.user;

import com.darfik.skycast.usersession.*;
import org.hibernate.HibernateException;

import java.util.NoSuchElementException;

public class UserService {
    private User user;
    private final UserDAO userDAO;
    private final UserSessionService userSessionService;
    private final PasswordEncryptor passwordEncryptor;

    UserService() {
        userDAO = UserDAO.getInstance();
        UserSessionServiceFactory userSessionServiceFactory = new UserSessionServiceFactory();
        userSessionService = UserSessionServiceFactory.build();
        passwordEncryptor = new PasswordEncryptor();
    }


    public void registerUser(UserDTO userDTO) throws HibernateException {
            User user = UserMapper.toModel(userDTO);
            if (userExists(user.getUsername())) {
                throw new HibernateException("User already exists"); //TODO Replace to custom exception
            } else {
                user.setPassword(passwordEncryptor.encryptPassword(userDTO.getPassword()));
                userDAO.save(user);
            }
    }

    public void authorizeUser(UserDTO userDTO, UserSessionDTO userSessionDTO) {
        if (userExists(userDTO.getUsername()) && isPasswordCorrect(userDTO)) {
            User user = userDAO.find(userDTO.getUsername()).get();
            userSessionService.updateUserSessions(user, userSessionDTO);
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
