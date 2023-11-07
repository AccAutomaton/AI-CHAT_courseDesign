package com.acautomaton.aichat.vo.chat;

import com.acautomaton.aichat.entity.dialog.SimpleDialog;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageVO {
    private List<SimpleMessageVO> messages = new ArrayList<>();

    public MessageVO(List<SimpleDialog> dialogList) {
        for (int i = 1; i < dialogList.size(); i++) {
            messages.add(new SimpleMessageVO(dialogList.get(i).getRole(), dialogList.get(i).getContent()));
        }
    }
}
