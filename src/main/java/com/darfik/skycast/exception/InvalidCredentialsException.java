package com.darfik.skycast.exception;

import jakarta.servlet.ServletException;

public class InvalidCredentialsException extends ServletException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
