package com.interviewTask.event.controller.exceptionHandler;

import org.postgresql.util.PSQLException;
import com.interviewTask.event.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger serviceLog = LoggerFactory.getLogger("service-log");
    private static final Logger authenticationLog = LoggerFactory.getLogger("auth-log");

    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        serviceLog.info(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchValidationException(ValidationException e) {
        serviceLog.info(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchPSQLException(PSQLException e) {
        serviceLog.info(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<AppError> catchAuthException(AuthException e) {
        authenticationLog.info(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchExpiredJwtException(ExpiredJwtException e) {
        authenticationLog.info(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchHasDeletedStatusException(HasDeletedStatusException e) {
        serviceLog.info(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getErrorMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
