package com.darfik.skycast;

import com.darfik.skycast.daos.UserDAO;
import com.darfik.skycast.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = UserDAO.getInstance();
    }

    @Test
    void save() {
        User newUser = new User();
        newUser.setLogin("Test");
        newUser.setPassword("Test");
        userDAO.save(newUser);

        Optional<User> userFromDBOptional = userDAO.get(3);
        if (userFromDBOptional.isPresent()) {
            User userFromDB = userFromDBOptional.get();
            assertNotNull(userFromDB);
        } else {
            fail("User not found in the database");
        }
    }
}
