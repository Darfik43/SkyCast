package com.darfik.skycast.exception;

import jakarta.servlet.ServletException;

public class AlreadyAddedLocationException extends ServletException {
    public AlreadyAddedLocationException(String message) {
        super(message);
    }
}
