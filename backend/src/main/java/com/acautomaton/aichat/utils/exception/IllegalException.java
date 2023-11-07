package com.acautomaton.aichat.utils.exception;

public class IllegalException extends Exception {
    public IllegalException() {
    }

    public IllegalException(String message) {
        super(message);
    }

    public IllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalException(Throwable cause) {
        super(cause);
    }

    public IllegalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
