package com.acautomaton.aichat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@TableName(value = "RECHARGE", schema = "AI_CHAT")
public class Recharge {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private Integer value;
    private Integer balance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @TableLogic
    @JsonIgnore
    private Integer deleteFlag;
}
