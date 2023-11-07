package com.acautomaton.aichat.controller.root;

import com.acautomaton.aichat.entity.User;
import com.acautomaton.aichat.entity.vipLevel.UserVipLevel;
import com.acautomaton.aichat.entity.vipLevel.VipLevelDescription;
import com.acautomaton.aichat.enums.UserStatus;
import com.acautomaton.aichat.enums.UserType;
import com.acautomaton.aichat.response.PageHelperResponse;
import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.service.inte.UserDetailService;
import com.acautomaton.aichat.service.inte.UserService;
import com.acautomaton.aichat.service.inte.vipLevel.UserVipLevelService;
import com.acautomaton.aichat.service.inte.vipLevel.VipLevelDescriptionService;
import com.acautomaton.aichat.utils.JudgeUnificationUtil;
import com.acautomaton.aichat.utils.exception.IllegalException;
import com.acautomaton.aichat.utils.exception.NullException;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/root/user")
public class _UserController {
    UserService userService;
    UserDetailService userDetailService;
    VipLevelDescriptionService vipLevelDescriptionService;
    UserVipLevelService userVipLevelService;
    @Autowired
    public _UserController(UserService userService, UserDetailService userDetailService, VipLevelDescriptionService vipLevelDescriptionService,
                           UserVipLevelService userVipLevelService) {
        this.userService = userService;
        this.userDetailService = userDetailService;
        this.vipLevelDescriptionService = vipLevelDescriptionService;
        this.userVipLevelService = userVipLevelService;
    }

    @GetMapping("/getAll/{pageNum}/{pageSize}")
    public Response getAllUserDetail(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageHelperResponse pageHelperResponse = new PageHelperResponse(
                PageHelper.startPage(pageNum, pageSize > 100 ? 100 : pageSize).doSelectPageInfo(() ->
                        userDetailService.getUsers())
        );
        return Response.success(pageHelperResponse);
    }

    @PostMapping("/update")
    public Response updateUserByUid(@RequestBody Map<String, Object> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("uid", "newNickname", "newStatus", "newUserType", "newVipLevel"));
            Integer uid = (Integer) request.get("uid");
            String newNickname = (String) request.get("newNickname");
            UserStatus newStatus = UserStatus.getByBoolean((Boolean) request.get("newStatus"));
            String newPhone = (String) (request.get("newPhone") == null ? "" : request.get("newPhone"));
            UserType newUserType = UserType.getByValueCn((String) request.get("newUserType"));

            String newVipLevel = (String) request.get("newVipLevel");
            VipLevelDescription vipLevelDescription = vipLevelDescriptionService.getDescriptionByName(newVipLevel);
            if (vipLevelDescription == null) {
                throw new NullException("会员等级不存在");
            }

            User user = userService.getUserByUid(uid);
            if (user == null) {
                throw new NullException("目标用户不存在");
            }
            UserVipLevel userVipLevel = userVipLevelService.getLevelByUid(uid);
            if (userVipLevel == null) {
                throw new NullException("目标会员不存在");
            }

            user.setNickname(newNickname);
            user.setStatus(newStatus);
            user.setPhone(newPhone);
            user.setUserType(newUserType);
            userVipLevel.setVipLevel(vipLevelDescription.getId());
            user.setUpdateTime(new Date());
            userService.updateUser(user);
            userVipLevelService.updateLevelByUid(userVipLevel);

            return Response.success();
        }
        catch (NullException | IllegalException e) {
            return Response.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{uid}")
    public Response deleteUserByUid(@PathVariable Integer uid) {
        try {
            User user = userService.getUserByUid(uid);
            if (user == null) {
                throw new NullException("用户不存在");
            }
            userService.deleteUserByUid(user);
            return Response.success();
        }
        catch (NullException e) {
            return Response.error(e.getMessage());
        }
    }
}
