package com.acautomaton.aichat.service.inte.dialog;

import com.acautomaton.aichat.entity.dialog.Dialog;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public interface DialogService {
    void addDialog(Dialog dialog);
    void deleteDialogById(Integer id);
    void updateTitleOfDialogById(Dialog dialog);
    void updateDialogListOfDialogById(Dialog dialog);
    Dialog getDialogById(Integer id);
    List<Dialog> getDialogListByUid(Integer uid);
    List<Dialog> getDialogListForRoot();
}
