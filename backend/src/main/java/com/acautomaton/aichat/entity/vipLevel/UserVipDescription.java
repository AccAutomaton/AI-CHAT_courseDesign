package com.acautomaton.aichat.entity.vipLevel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@TableName(value = "USER_VIP_DESCRIPTION", schema = "AI_CHAT")
public class UserVipDescription implements Serializable {
    @TableId(type = IdType.INPUT)
    private Integer uid;
    private Integer vipLevel;
    private String description;
}
