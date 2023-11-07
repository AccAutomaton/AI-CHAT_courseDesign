package com.acautomaton.aichat.utils.exception;

public class PresenceException extends Exception{
    public PresenceException() {
    }

    public PresenceException(String message) {
        super(message);
    }

    public PresenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresenceException(Throwable cause) {
        super(cause);
    }

    public PresenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
