package com.cloud.user.microservice.exception;

public class UserAuthException extends RuntimeException {

    public UserAuthException() {
        super();
    }

    public UserAuthException(String message) {
        super(message);
    }

    public UserAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAuthException(Throwable cause) {
        super(cause);
    }

    protected UserAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
