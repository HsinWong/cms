package com.hsinwong.cms.data;

import com.hsinwong.cms.data.bean.User;
import com.hsinwong.cms.security.UserHelper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
class SpringSecurityAuditorAware implements AuditorAware<User> {

    public Optional<User> getCurrentAuditor() {
        return Optional.of(UserHelper.getCurrentUser());
    }
}