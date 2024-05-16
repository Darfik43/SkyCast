package com.darfik.skycast.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
