package com.hsinwong.cms.repository;

import com.hsinwong.cms.bean.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByUsername(String username);
}
