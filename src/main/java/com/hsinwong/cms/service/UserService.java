package com.hsinwong.cms.service;

import com.hsinwong.cms.bean.User;
import com.hsinwong.cms.repository.UserRepository;
import com.hsinwong.cms.security.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public boolean verifyPassword(String password) {
        User currentUser = UserHelper.getCurrentUser();
        return passwordEncoder.matches(password, currentUser.getPassword());
    }

    public void modifyPassword(String password) {
        User currentUser = UserHelper.getCurrentUser();
        currentUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(currentUser);
    }
}
