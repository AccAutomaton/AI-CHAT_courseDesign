package com.acautomaton.aichat.utils;

import com.acautomaton.aichat.entity.SecurityUser;
import com.acautomaton.aichat.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalUtil {
    public static User getUser() {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityUser.getUser();
    }
}
