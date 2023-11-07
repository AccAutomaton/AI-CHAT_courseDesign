package com.acautomaton.aichat.controller.user;

import com.acautomaton.aichat.entity.User;
import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.service.inte.UserService;
import com.acautomaton.aichat.service.inte.vipLevel.UserVipDescriptionService;
import com.acautomaton.aichat.utils.JudgeUnificationUtil;
import com.acautomaton.aichat.utils.PrincipalUtil;
import com.acautomaton.aichat.utils.exception.IllegalException;
import com.acautomaton.aichat.utils.exception.NullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;
    UserVipDescriptionService userVipDescriptionService;
    PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UserService userService, UserVipDescriptionService userVipDescriptionService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userVipDescriptionService = userVipDescriptionService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/get/roughInformation")
    public Response getRoughInformation() {
        User user = PrincipalUtil.getUser();
        return Response.success(
                Map.ofEntries(
                        Map.entry("uid", user.getUid()),
                        Map.entry("username", user.getUsername()),
                        Map.entry("nickname", user.getNickname()),
                        Map.entry("userType", user.getUserType())
                )
        );
    }

    @GetMapping("/get/Information")
    public Response getInformation() {
        User user = PrincipalUtil.getUser();
        String vipLevel = userVipDescriptionService.getUserVipDescriptionByUid(user.getUid()).getDescription();
        return Response.success(Map.of("user", user, "vipLevel", vipLevel));
    }

    @PostMapping("/set/nickname")
    public Response setNickname(@RequestBody Map<String, String> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("newNickname"));
            String newNickname = request.get("newNickname");
            User user = PrincipalUtil.getUser();
            user.setNickname(newNickname);
            user.setUpdateTime(new Date());
            userService.updateUser(user);
            return Response.success();
        }
        catch (NullException e) {
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/set/phone")
    public Response setPhone(@RequestBody Map<String, String> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("newPhone"));
            String newPhone = request.get("newPhone");
            if (!JudgeUnificationUtil.isLegalPhone(newPhone)) {
                throw new IllegalException("手机号非法");
            }
            User user = PrincipalUtil.getUser();
            user.setPhone(newPhone);
            user.setUpdateTime(new Date());
            userService.updateUser(user);
            return Response.success();
        }
        catch (NullException | IllegalException e) {
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/set/password")
    public Response setPassword(@RequestBody Map<String, String> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("oldPassword", "newPassword"));
            String oldPassword = request.get("oldPassword");
            String newPassword = request.get("newPassword");
            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                throw new NullException("密码不能为空!");
            }
            if (oldPassword.equals(newPassword)) {
                throw new IllegalException("新密码不能与旧密码一致");
            }

            User user = PrincipalUtil.getUser();
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new IllegalException("原密码错误");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.updateUser(user);
            return Response.success(Map.of("status", "success"));
        }
        catch (NullException | IllegalException e) {
            return Response.error(e.getMessage());
        }
    }
}
