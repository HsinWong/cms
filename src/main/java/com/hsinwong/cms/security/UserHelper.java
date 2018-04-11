package com.hsinwong.cms.security;

import com.hsinwong.cms.bean.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class UserHelper {

    public Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetail) {
            return Optional.ofNullable(((UserDetail) principal).getUser());
        }
        return Optional.empty();
    }

}
