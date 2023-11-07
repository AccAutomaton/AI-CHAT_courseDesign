package com.acautomaton.aichat.vo.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageVO {
    private Integer prompt_tokens;
    private Integer completion_tokens;
    private Integer total_tokens;
}
