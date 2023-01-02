package com.travelapp.backend.controllers.advicer;

import com.travelapp.backend.responses.ResponseObject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseObject entityExistsException(Exception message) {
        return new ResponseObject(HttpStatus.CONFLICT.value(), message.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseObject badCredentialsException(Exception message) {
        return new ResponseObject(HttpStatus.CONFLICT.value(), message.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseObject entityNotFoundException(Exception message) {
        return new ResponseObject(HttpStatus.CONFLICT.value(), message.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseObject usernameNotFoundException(Exception exception) {
        return new ResponseObject(HttpStatus.CONFLICT.value(), exception.getMessage());
    }
}
