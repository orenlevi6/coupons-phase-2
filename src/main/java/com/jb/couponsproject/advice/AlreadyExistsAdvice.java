package com.jb.couponsproject.advice;

import com.jb.couponsproject.exceptions.AlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@ControllerAdvice
public class AlreadyExistsAdvice {
    @ExceptionHandler(value = {AlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleAlreadyExistsException(Exception err) {
        return new ErrorDetails("Already Exists Error", err.getMessage());
    }

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleUniqueException(Exception err) {
        return new ErrorDetails("Unique Value Error", "Name or email already exists");
    }

}
