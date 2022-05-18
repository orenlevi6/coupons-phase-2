package com.jb.couponsproject.exceptions;

public class TokenException extends Exception {
    public TokenException() {
        super("Token Exception");
    }

    public TokenException(String message) {
        super(message);
    }

}
