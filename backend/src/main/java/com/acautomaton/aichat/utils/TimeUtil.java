package com.acautomaton.aichat.utils;

import com.acautomaton.aichat.utils.exception.OtherException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static final ObjectMapper timestampMapper = new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    public static String SerializeTimestamp(Date date) throws OtherException {
        try {
            String s = timestampMapper.writeValueAsString(date);
            s = s.substring(1);
            return s.substring(0, s.length() - 1);
        }
        catch (JsonProcessingException e) {
            throw new OtherException("系统错误, 请稍后重试");
        }
    }
}
