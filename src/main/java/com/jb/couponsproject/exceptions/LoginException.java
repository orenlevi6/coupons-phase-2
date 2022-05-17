package com.jb.couponsproject.exceptions;

public class LoginException extends Exception {
    public LoginException() {
        super("Login Exception");
    }

    public LoginException(String message) {
        super(message);
    }

}
