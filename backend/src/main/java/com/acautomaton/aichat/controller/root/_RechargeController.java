package com.acautomaton.aichat.controller.root;

import com.acautomaton.aichat.response.PageHelperResponse;
import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.service.inte.RechargeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/root/recharge")
public class _RechargeController {
    RechargeService rechargeService;
    @Autowired
    public _RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @GetMapping("/getList/{pageNum}/{pageSize}")
    public Response getRechargeList(@PathVariable Integer pageNum, @PathVariable Integer pageSize,
                                    @RequestParam(value = "uid", defaultValue = "0") Integer uid,
                                    @RequestParam(value = "mode", defaultValue = "none") String mode) {
        PageHelperResponse pageHelperResponse = new PageHelperResponse(
                PageHelper.startPage(pageNum, pageSize > 100 ? 100 : pageSize).doSelectPageInfo(() ->
                        rechargeService.getRechargesForRoot(uid, mode))
        );
        return Response.success(pageHelperResponse);
    }
}
