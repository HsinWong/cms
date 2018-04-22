package com.hsinwong.cms.security;

import com.hsinwong.cms.bean.User;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;

public final class UserHelper {

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetail) {
            return ((UserDetail) principal).getUser();
        }
        throw new AuthenticationServiceException("无法获取当前登录的用户信息");
    }

}
