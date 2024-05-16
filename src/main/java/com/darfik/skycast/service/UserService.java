package com.darfik.skycast.service;

import com.darfik.skycast.dao.UserDAO;
import com.darfik.skycast.exception.DatabaseException;
import com.darfik.skycast.exception.InvalidCredentialsException;
import com.darfik.skycast.exception.UserAlreadyExistsException;
import com.darfik.skycast.model.User;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.model.dto.UserSessionDTO;
import org.hibernate.HibernateException;

public class UserService {
    private final UserDAO userDAO;
    private final UserSessionService userSessionService;
    private final PasswordEncryptor passwordEncryptor;

    public UserService() {
        this.userDAO = UserDAO.getInstance();
        this.userSessionService = new UserSessionService();
        this.passwordEncryptor = new PasswordEncryptor();
    }


    public void registerUser(UserDTO userDTO) throws UserAlreadyExistsException, DatabaseException {
            User user = UserMapper.toModel(userDTO);
        if (userExists(user.getUsername())) {
            throw new UserAlreadyExistsException("User already exists");
        } else {
            try {
                user.setPassword(passwordEncryptor.encryptPassword(userDTO.getPassword()));
                userDAO.save(user);
            } catch (HibernateException e) {
                throw new DatabaseException("Error saving user", e);
            }
        }
    }

    public void authorizeUser(UserDTO userDTO, UserSessionDTO userSessionDTO) throws InvalidCredentialsException {
        if (userExists(userDTO.getUsername()) && isPasswordCorrect(userDTO)) {
            User user = userDAO.find(userDTO.getUsername()).get();
            userSessionService.updateUserSessions(user, userSessionDTO);
        } else {
            throw new InvalidCredentialsException("Incorrect username or password");
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
