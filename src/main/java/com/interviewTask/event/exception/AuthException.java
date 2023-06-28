package com.interviewTask.event.exception;

public class AuthException extends RuntimeException {
    public AuthException(String errorMessage) {
        super(errorMessage);
    }
}
