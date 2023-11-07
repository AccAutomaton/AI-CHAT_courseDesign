package com.acautomaton.aichat.enums;

import com.acautomaton.aichat.utils.exception.IllegalException;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType {
    ROOT(0, "ROOT", "根用户"),
    ADMIN(1, "ADMIN", "管理员"),
    USER(2, "USER", "用户");


    @EnumValue
    private final int id;
    private final String value;
    @JsonValue
    private final String value_cn;

    UserType(int id, String value, String value_cn) {
        this.id = id;
        this.value = value;
        this.value_cn = value_cn;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static UserType getById(int id) throws IllegalException {
        for (UserType value: values()) {
            if (value.id == id) {
                return value;
            }
        }
        throw new IllegalException("非法的枚举值");
    }

    public static UserType getByValueCn(String valueCn) throws IllegalException {
        for (UserType value: values()) {
            if (value.value_cn.equals(valueCn)) {
                return value;
            }
        }
        throw new IllegalException("非法的枚举值");
    }
}
