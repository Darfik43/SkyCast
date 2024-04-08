package com.darfik.skycast.services;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    public String encryptPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashedPassword;
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
