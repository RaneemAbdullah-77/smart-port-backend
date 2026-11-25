package com.smartport.auth.application.exception;

import lombok.Getter;

/** Business error carrying the HTTP status the presentation layer should answer with. */
@Getter
public class AuthException extends RuntimeException {

    private final int status;

    public AuthException(int status, String message) {
        super(message);
        this.status = status;
    }

    public static AuthException unauthorized(String message) {
        return new AuthException(401, message);
    }

    public static AuthException badRequest(String message) {
        return new AuthException(400, message);
    }
}
