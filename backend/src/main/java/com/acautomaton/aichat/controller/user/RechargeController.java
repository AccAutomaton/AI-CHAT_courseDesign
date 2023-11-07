package com.acautomaton.aichat.controller.user;

import com.acautomaton.aichat.entity.Recharge;
import com.acautomaton.aichat.entity.User;
import com.acautomaton.aichat.response.PageHelperResponse;
import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.service.inte.RechargeService;
import com.acautomaton.aichat.service.inte.UserService;
import com.acautomaton.aichat.utils.JudgeUnificationUtil;
import com.acautomaton.aichat.utils.PrincipalUtil;
import com.acautomaton.aichat.utils.exception.IllegalException;
import com.acautomaton.aichat.utils.exception.NullException;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recharge")
public class RechargeController {
    RechargeService rechargeService;
    UserService userService;
    @Autowired
    public RechargeController(RechargeService rechargeService, UserService userService) {
        this.rechargeService = rechargeService;
        this.userService = userService;
    }

    @GetMapping("/getBalance")
    public Response getBalance() {
        User user = PrincipalUtil.getUser();
        return Response.success(Map.of("balance", user.getBalance()));
    }

    @GetMapping("/get/{rechargeId}")
    public Response getRecharge(@PathVariable Integer rechargeId) {
        try {
            User user = PrincipalUtil.getUser();
            Recharge recharge = rechargeService.getRechargeById(rechargeId);
            if (recharge == null) {
                throw new NullException("交易不存在");
            }
            if (!recharge.getUid().equals(user.getUid())) {
                throw new IllegalException("无权访问");
            }
            return Response.success(recharge);
        }
        catch (NullException | IllegalException e) {
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/getList/{pageNum}/{pageSize}")
    public Response getRechargeList(@PathVariable Integer pageNum, @PathVariable Integer pageSize,
                                    @RequestParam(value = "mode", defaultValue = "none") String mode) {
        User user = PrincipalUtil.getUser();
        PageHelperResponse pageHelperResponse = new PageHelperResponse(
                PageHelper.startPage(pageNum, pageSize > 100 ? 100 : pageSize).doSelectPageInfo(() ->
                        rechargeService.getRechargesByUid(user.getUid(), mode))
        );
        return Response.success(pageHelperResponse);
    }

    @PostMapping("/do")
    public Response DoRecharge(@RequestBody Map<String, Integer> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("value"));
            Integer value = request.get("value");
            User user = PrincipalUtil.getUser();
            Integer newBalance = user.getBalance() + value;
            user.setBalance(newBalance);
            Date nowDate = new Date();
            user.setUpdateTime(nowDate);
            userService.updateBalanceByUid(user);
            rechargeService.addRecharge(new Recharge(null, user.getUid(), value, newBalance, nowDate, 0));
            return Response.success(Map.of("newBalance", newBalance));
        }
        catch (NullException e) {
            return Response.error(e.getMessage());
        }
    }
}
