package com.acautomaton.aichat.entity.vipLevel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "USER_VIP_LEVEL", schema = "AI_CHAT")
public class UserVipLevel implements Serializable {
    @TableId(type = IdType.INPUT)
    private Integer uid;
    private Integer vipLevel;
}
