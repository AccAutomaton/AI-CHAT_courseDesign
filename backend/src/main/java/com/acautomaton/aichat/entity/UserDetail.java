package com.acautomaton.aichat.entity;

import com.acautomaton.aichat.enums.UserStatus;
import com.acautomaton.aichat.enums.UserType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@TableName(value = "USER_DETAIL", schema = "AI_CHAT")
public class UserDetail implements Serializable {
    @TableId
    private Integer uid;
    private String username;
    private String nickname;
    private UserStatus status;
    private String phone;
    private UserType userType;
    private Integer balance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    @TableLogic
    @JsonIgnore
    private Integer deleteFlag;
    private String vipLevel;
}
