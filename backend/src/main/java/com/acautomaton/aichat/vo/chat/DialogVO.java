package com.acautomaton.aichat.vo.chat;

import cn.hutool.json.JSONUtil;
import com.acautomaton.aichat.entity.dialog.Dialog;
import com.acautomaton.aichat.entity.dialog.SimpleDialog;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class DialogVO {
    private Integer id;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer uid;
    private String title;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<SimpleDialog> dialogList;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Date updateTime;
    @JsonIgnore
    private Integer deleteFlag;

    public DialogVO(Dialog dialog) {
        this.id = dialog.getId();
        this.uid = dialog.getUid();
        this.title = dialog.getTitle();
        this.createTime = dialog.getCreateTime();
        this.updateTime = dialog.getUpdateTime();
        this.deleteFlag = dialog.getDeleteFlag();
        this.dialogList = JSONUtil.toList(dialog.getDialogList(), SimpleDialog.class);
    }
}
