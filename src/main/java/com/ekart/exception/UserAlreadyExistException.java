package com.ekart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(String msg) {
        super(msg);
    }

    public UserAlreadyExistException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
