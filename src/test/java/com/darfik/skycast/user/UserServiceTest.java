package com.darfik.skycast.user;

import com.darfik.skycast.dao.UserDAO;
import com.darfik.skycast.exception.DatabaseException;
import com.darfik.skycast.exception.UserAlreadyExistsException;
import com.darfik.skycast.model.User;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.service.PasswordEncryptor;
import com.darfik.skycast.service.UserService;
import com.darfik.skycast.service.UserSessionService;
import com.darfik.skycast.util.HibernateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;
    private static UserDAO userDAO;
    @Mock
    private UserSessionService userSessionService;
    private PasswordEncryptor passwordEncryptor;


    @BeforeAll
    public static void init() {
        HibernateUtil.useTestConfiguration(true);
        userDAO = UserDAO.getInstance();
        }

    @BeforeEach
    public void setUp() {
        userSessionService = mock(UserSessionService.class);
        passwordEncryptor = mock(PasswordEncryptor.class);
        userService = new UserService(userDAO, userSessionService, passwordEncryptor);
    }

    @Test
    public void testRegisterUser_Success() throws UserAlreadyExistsException, DatabaseException {
        UserDTO userDTO = new UserDTO("testUser", "password");

        when(passwordEncryptor.encryptPassword("password")).thenReturn("encryptedPassword");
        userService.registerUser(userDTO);

        Optional<User> userOptional = userDAO.find("testUser");
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals(userDTO.getUsername(), user.getUsername());
    }
    @Test
    public void testRegisterUser_UserAlreadyExists() {
        UserDTO userDTO = new UserDTO("testUser", "password");

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(userDTO));
    }
}
