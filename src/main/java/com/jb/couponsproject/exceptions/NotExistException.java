package com.jb.couponsproject.exceptions;

public class NotExistException extends Exception {
    public NotExistException() {
        super("Not Exist Exception");
    }

    public NotExistException(String message) {
        super(message);
    }

}
