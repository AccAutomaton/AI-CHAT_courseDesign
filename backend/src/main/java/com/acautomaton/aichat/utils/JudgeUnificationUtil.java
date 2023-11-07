package com.acautomaton.aichat.utils;

import com.acautomaton.aichat.utils.exception.NullException;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class JudgeUnificationUtil {
    public static <T> void parameterNotNull(Map<String, T> param, List<String> keys) throws NullException {
        for (String key: keys) {
            String value = param.get(key).toString();
            if (param.get(key) == null || value == null || value.trim().isEmpty()) {
                throw new NullException(key + " can not be empty !");
            }
        }
    }

    public static boolean isLegalEmail(String s) {
        return Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", s);
    }

    public static boolean isLegalPhone(String s) {
        return Pattern.matches("^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$", s);
    }

    public static boolean isLegalUsername(String s) {
        return Pattern.matches("^[a-zA-Z0-9_-]{4,16}$", s);
    }

    public static boolean isLegalPassword(String s) {
        return Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", s);
    }

    public static boolean isLegalCodeType(String s) {
        return "markdown".equals(s);
    }

    public static boolean isLegalVerifyCode(String s) {
        return Pattern.matches("^\\d{6}$", s);
    }
}
