package com.acautomaton.aichat.vo.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallbackVO {
    private String id;
    private String object;
    private Integer created;
    private String result;
    private Boolean is_truncated;
    private Boolean need_clean_history;
    private UsageVO usage;
}
