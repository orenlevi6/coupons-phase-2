package com.jb.couponsproject.exceptions;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException() {
        super("Already Exists Exception");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

}
