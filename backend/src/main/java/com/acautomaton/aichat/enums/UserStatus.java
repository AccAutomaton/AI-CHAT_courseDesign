package com.acautomaton.aichat.enums;

import com.acautomaton.aichat.utils.exception.IllegalException;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserStatus {
    NORMAL(0, "正常"), LOCKED(1, "锁定");

    @EnumValue
    private final int id;
    @JsonValue
    private final String value;

    UserStatus(int id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static UserStatus getById(int id) throws IllegalException {
        for (UserStatus value: values()) {
            if (value.id == id) {
                return value;
            }
        }
        throw new IllegalException("非法的枚举值");
    }

    public static UserStatus getByBoolean(Boolean b) {
        return b ? NORMAL : LOCKED;
    }
}
