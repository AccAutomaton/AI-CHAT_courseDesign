package com.acautomaton.aichat.controller.user;

import com.acautomaton.aichat.entity.User;
import com.acautomaton.aichat.entity.vipLevel.UserVipLevel;
import com.acautomaton.aichat.enums.UserStatus;
import com.acautomaton.aichat.enums.UserType;
import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.service.inte.UserService;
import com.acautomaton.aichat.service.inte.vipLevel.UserVipLevelService;
import com.acautomaton.aichat.utils.JudgeUnificationUtil;
import com.acautomaton.aichat.utils.JwtUtil;
import com.acautomaton.aichat.utils.exception.IllegalException;
import com.acautomaton.aichat.utils.exception.NullException;
import com.acautomaton.aichat.utils.exception.OtherException;
import com.acautomaton.aichat.utils.exception.PresenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    UserService userService;
    UserVipLevelService userVipLevelService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserService userService, PasswordEncoder passwordEncoder, UserVipLevelService userVipLevelService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userVipLevelService = userVipLevelService;
    }

    @PostMapping("/register")
    public Response register(@RequestBody Map<String, String> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("username", "password"));
            String username = request.get("username");
            String password = request.get("password");

            if (!JudgeUnificationUtil.isLegalUsername(username)) {
                throw new IllegalException("用户名不合法: 应由4-16位字母,数字,'_','-'构成");
            }
            if (!JudgeUnificationUtil.isLegalPassword(password)) {
                throw new IllegalException("密码不合法：至少8位,且同时包含字母和数字(不含特殊符号)");
            }

            User user = userService.getUserByUsername(username);
            if (user != null) {
                throw new PresenceException("用户名已存在");
            }

            user = new User(null, username, passwordEncoder.encode(password), username, UserStatus.NORMAL, "",
                    UserType.USER, 0, new Date(), new Date(), 0);
            user = userService.registerUser(user);
            if (user.getUid() <= 100000000) {
                throw new OtherException("System Error");
            }
            userVipLevelService.addLevel(new UserVipLevel(user.getUid(), 1));
            log.info("用户 " + user.getUid() + " 注册成功");
        }
        catch (NullException | IllegalException | PresenceException | OtherException e) {
            return Response.error(e.getMessage());
        }
        return Response.success();
    }

    @PostMapping("/login")
    public Response login(@RequestBody Map<String, String> request) {
        String authorization;
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("username", "password"));
            String username = request.get("username");
            String password = request.get("password");

            if (!JudgeUnificationUtil.isLegalUsername(username)) {
                throw new IllegalException("用户名不合法: 应由4-16位字母,数字,'_','-'构成");
            }
            if (!JudgeUnificationUtil.isLegalPassword(password)) {
                throw new IllegalException("密码不合法：至少8位,且同时包含字母和数字(不含特殊符号)");
            }

            User user = userService.getUserByUsername(username);
            if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
                throw new UsernameNotFoundException("用户名或密码错误");
            }

            authorization = "Bearer " + JwtUtil.getToken(Map.ofEntries(
                    Map.entry("username", username),
                    Map.entry("uid", user.getUid().toString())
            ));
            log.info("用户 " + user.getUid() + " 登陆成功");
            return Response.success(Map.ofEntries(
                    Map.entry("Authorization", authorization),
                    Map.entry("uid", user.getUid()),
                    Map.entry("username", user.getUsername()),
                    Map.entry("nickname", user.getNickname()),
                    Map.entry("userType", user.getUserType())
            ));
        }
        catch (NullException | IllegalException | UsernameNotFoundException e) {
            return Response.error(e.getMessage());
        }
    }
}
