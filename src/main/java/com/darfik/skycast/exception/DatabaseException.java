package com.darfik.skycast.exception;

import jakarta.servlet.ServletException;

public class DatabaseException extends ServletException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
