package com.jb.couponsproject.exceptions;

import com.jb.couponsproject.beans.ClientType;

public class LoginException extends Exception {

    public LoginException(String message) {
        super(message);
    }

    public LoginException(ClientType clientType) {
        super("Invalid login " + clientType.toString());
    }

}
