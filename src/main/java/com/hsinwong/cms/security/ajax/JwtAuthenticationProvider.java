package com.hsinwong.cms.security.ajax;

import com.hsinwong.cms.data.bean.User;
import com.hsinwong.cms.data.repository.UserRepository;
import com.hsinwong.cms.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwtToken = (String) authentication.getCredentials();

        Optional<Long> userId = JwtTokenUtils.verifyToken(jwtToken);
        if (userId.isPresent()) {
            Optional<User> user = userRepository.findById(userId.get());
            if (user.isPresent()) {
                UserDetail userDetail = new UserDetail(user.get());
                return new UsernamePasswordAuthenticationToken(
                        userDetail, userDetail.getPassword(), userDetail.getAuthorities());
            }
        }
        throw new BadCredentialsException("Token已失效");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}