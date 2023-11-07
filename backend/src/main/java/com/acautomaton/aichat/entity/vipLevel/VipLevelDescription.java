package com.acautomaton.aichat.entity.vipLevel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(value = "VIP_LEVEL_DESCRIPTION", schema = "AI_CHAT")
public class VipLevelDescription {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String description;
}
