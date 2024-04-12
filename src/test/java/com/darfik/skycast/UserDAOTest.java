package com.darfik.skycast;

import com.darfik.skycast.user.UserDAO;
import com.darfik.skycast.user.User;
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



}
