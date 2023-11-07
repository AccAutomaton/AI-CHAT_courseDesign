package com.acautomaton.aichat.enums;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    SUCCESS("success"), ERROR("error"), JWT_ERROR("jwtError"), SPEED_ERROR("speedError");

    private final String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
