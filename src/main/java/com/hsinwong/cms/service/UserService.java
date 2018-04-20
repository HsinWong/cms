package com.hsinwong.cms.service;

import com.hsinwong.cms.bean.User;
import com.hsinwong.cms.repository.UserRepository;
import com.hsinwong.cms.security.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void modifyPassword(String oldPassword, String newPassword) {
        Optional<User> currentUser = userHelper.getCurrentUser();
        if (currentUser.isPresent()) {
            if (passwordEncoder.matches(oldPassword, currentUser.get().getPassword())) {
                currentUser.get().setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(currentUser.get());
            } else {
                throw new UnsupportedOperationException("原密码不正确，无法修改密码");
            }
        } else {
            throw new UnsupportedOperationException("用户不存在，无法修改密码");
        }
    }
}
