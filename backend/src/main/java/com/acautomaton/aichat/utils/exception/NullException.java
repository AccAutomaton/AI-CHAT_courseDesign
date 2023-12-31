package com.acautomaton.aichat.utils.exception;

public class NullException extends Exception{
    public NullException() {
    }

    public NullException(String message) {
        super(message);
    }

    public NullException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullException(Throwable cause) {
        super(cause);
    }

    public NullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
