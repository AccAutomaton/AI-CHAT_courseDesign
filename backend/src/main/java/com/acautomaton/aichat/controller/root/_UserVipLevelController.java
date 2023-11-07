package com.acautomaton.aichat.controller.root;

import com.acautomaton.aichat.entity.vipLevel.UserVipLevel;
import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.service.inte.vipLevel.UserVipLevelService;
import com.acautomaton.aichat.service.inte.vipLevel.VipLevelDescriptionService;
import com.acautomaton.aichat.utils.JudgeUnificationUtil;
import com.acautomaton.aichat.utils.exception.NullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/root/user")
public class _UserVipLevelController {
    UserVipLevelService userVipLevelService;
    VipLevelDescriptionService vipLevelDescriptionService;
    @Autowired
    public _UserVipLevelController(UserVipLevelService userVipLevelService, VipLevelDescriptionService vipLevelDescriptionService) {
        this.userVipLevelService = userVipLevelService;
        this.vipLevelDescriptionService = vipLevelDescriptionService;
    }

    @PostMapping("/set/vipLevel")
    public Response setVipLevel(@RequestBody Map<String, Integer> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("uid", "newLevel"));
            Integer uid = request.get("uid");
            Integer newLevel = request.get("newLevel");
            if (vipLevelDescriptionService.getDescriptionById(newLevel) == null) {
                throw new NullException("等级不存在");
            }

            UserVipLevel userVipLevel = userVipLevelService.getLevelByUid(uid);
            if (userVipLevel == null) {
                throw new NullException("用户会员信息不存在");
            }
            userVipLevel.setVipLevel(newLevel);
            userVipLevelService.updateLevelByUid(userVipLevel);
            log.info("update uid: " + uid + "'s vip level to: " + newLevel + " success");
            return Response.success();
        }
        catch (NullException e) {
            return Response.error(e.getMessage());
        }
    }
}
