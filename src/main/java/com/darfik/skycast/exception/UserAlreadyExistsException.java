package com.darfik.skycast.exception;

import jakarta.servlet.ServletException;

public class UserAlreadyExistsException extends ServletException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
}
