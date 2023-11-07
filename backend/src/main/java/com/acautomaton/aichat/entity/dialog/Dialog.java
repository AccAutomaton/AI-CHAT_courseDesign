package com.acautomaton.aichat.entity.dialog;

import cn.hutool.json.JSONUtil;
import com.acautomaton.aichat.vo.chat.DialogVO;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@TableName(value = "DIALOG", schema = "AI_CHAT", autoResultMap = true)
public class Dialog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer uid;
    private String title;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String dialogList;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Date updateTime;
    @TableLogic
    @JsonIgnore
    private Integer deleteFlag;

    public Dialog(DialogVO dialogVO) {
        this.id = dialogVO.getId();
        this.uid = dialogVO.getUid();
        this.title = dialogVO.getTitle();
        this.createTime = dialogVO.getCreateTime();
        this.updateTime = dialogVO.getUpdateTime();
        this.deleteFlag = dialogVO.getDeleteFlag();
        this.dialogList = JSONUtil.toJsonStr(dialogVO.getDialogList());
    }

    public Dialog(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Dialog(Integer id, Integer uid, String title, Date createTime, Date updateTime) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
