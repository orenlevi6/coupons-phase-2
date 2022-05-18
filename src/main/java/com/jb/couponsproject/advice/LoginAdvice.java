package com.jb.couponsproject.advice;

import com.jb.couponsproject.exceptions.LoginException;
import com.jb.couponsproject.exceptions.TokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class LoginAdvice {
    @ExceptionHandler(value = {LoginException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleLoginException(Exception err) {
        return new ErrorDetails("Login Error", err.getMessage());
    }

    @ExceptionHandler(value = {TokenException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleTokenException(Exception err) {
        return new ErrorDetails("Token Error", err.getMessage());
    }

}
