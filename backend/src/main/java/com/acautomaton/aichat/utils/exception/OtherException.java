package com.acautomaton.aichat.utils.exception;

public class OtherException extends Exception{
    public OtherException() {
    }

    public OtherException(String message) {
        super(message);
    }

    public OtherException(String message, Throwable cause) {
        super(message, cause);
    }

    public OtherException(Throwable cause) {
        super(cause);
    }

    public OtherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
