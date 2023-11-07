package com.acautomaton.aichat.entity.dialog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleDialog {
    private String role;
    private String time;
    private String content;
}
