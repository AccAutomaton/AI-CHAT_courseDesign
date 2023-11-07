package com.acautomaton.aichat.controller.root;

import com.acautomaton.aichat.response.PageHelperResponse;
import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.service.inte.dialog.DialogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/root/chat")
public class _ChatController {
    DialogService dialogService;
    @Autowired
    public _ChatController(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @GetMapping("/getList/{pageNum}/{pageSize}")
    public Response getList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageHelperResponse pageHelperResponse = new PageHelperResponse(
                PageHelper.startPage(pageNum, pageSize > 100 ? 100 : pageSize).doSelectPageInfo(() ->
                        dialogService.getDialogListForRoot())
        );
        return Response.success(pageHelperResponse);
    }
}
