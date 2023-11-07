package com.acautomaton.aichat.vo.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleMessageVO {
    private String role;
    private String content;
}
