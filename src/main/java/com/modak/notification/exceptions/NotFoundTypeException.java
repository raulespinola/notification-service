package com.modak.notification.exceptions;

public class NotFoundTypeException extends RuntimeException {

    public NotFoundTypeException(String message) {
        super(message);
    }

    public NotFoundTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}