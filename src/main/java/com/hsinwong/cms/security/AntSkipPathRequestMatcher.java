package com.hsinwong.cms.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class AntSkipPathRequestMatcher implements RequestMatcher {

    private AntPathRequestMatcher antPathRequestMatcher;
    private AntPathRequestMatcher[] skipAntPathRequestMatchers;

    public AntSkipPathRequestMatcher(String pattern, String... skipPatterns) {
        antPathRequestMatcher = new AntPathRequestMatcher(pattern);
        skipAntPathRequestMatchers = new AntPathRequestMatcher[skipPatterns.length];
        for (int i = 0; i < skipAntPathRequestMatchers.length; i++) {
            skipAntPathRequestMatchers[i] = new AntPathRequestMatcher(skipPatterns[i]);
        }
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        for (AntPathRequestMatcher skipAntPathRequestMatcher : skipAntPathRequestMatchers) {
            if (skipAntPathRequestMatcher.matches(request)) {
                return false;
            }
        }
        return antPathRequestMatcher.matches(request);
    }
}
