package com.acautomaton.aichat.utils.exception;

public class TooFastException extends Exception{
    public TooFastException() {
    }

    public TooFastException(String message) {
        super(message);
    }

    public TooFastException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooFastException(Throwable cause) {
        super(cause);
    }

    public TooFastException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
