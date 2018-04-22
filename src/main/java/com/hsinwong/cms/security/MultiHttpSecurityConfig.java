package com.hsinwong.cms.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsinwong.cms.bean.User;
import com.hsinwong.cms.constant.MessageConstants;
import com.hsinwong.cms.repository.UserRepository;
import com.hsinwong.cms.security.ajax.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MultiHttpSecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                return new UserDetail(user.get());
            } else {
                throw new UsernameNotFoundException("用户名不存在");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private AjaxAwareAuthenticationSuccessHandler authenticationSuccessHandler;

        @Autowired
        private AjaxAwareAuthenticationFailureHandler authenticationFailureHandler;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private JwtAuthenticationProvider jwtAuthenticationProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors().and()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers("/api/login").permitAll()
                    .anyRequest().fullyAuthenticated()
                    .and()
                    .csrf().disable()
                    .addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .userDetailsService(userDetailsService);
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE"));
            configuration.setAllowedHeaders(Arrays.asList("Content-Type", "X-Requested-With", "Authorization"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/api/**", configuration);
            return source;
        }

        @Bean
        public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
            AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter(
                    "/api/login",
                    authenticationSuccessHandler, authenticationFailureHandler, objectMapper);
            ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
            return ajaxLoginProcessingFilter;
        }

        @Bean
        public JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter() throws Exception {
            JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter =
                    new JwtTokenAuthenticationProcessingFilter(
                            new AntSkipPathRequestMatcher("/api/**", "/api/login"),
                            authenticationFailureHandler);
            jwtTokenAuthenticationProcessingFilter.setAuthenticationManager(authenticationManagerBean());
            return jwtTokenAuthenticationProcessingFilter;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(jwtAuthenticationProvider);
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;

        @Value("${hsinwong.security.remeberMe.tokenValiditySeconds}")
        private Integer tokenValiditySeconds;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/sys/**")
                    .authorizeRequests()
                    .antMatchers("/assets/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll()
                    .and()
                    .logout().permitAll()
                    .and()
                    .rememberMe().userDetailsService(userDetailsService).tokenValiditySeconds(tokenValiditySeconds);
        }
    }
}