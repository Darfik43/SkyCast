package com.darfik.skycast;

import com.darfik.skycast.user.UserDAO;
import org.junit.jupiter.api.BeforeEach;

public class UserDAOTest {

    //TODO
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = UserDAO.getInstance();
    }


}
