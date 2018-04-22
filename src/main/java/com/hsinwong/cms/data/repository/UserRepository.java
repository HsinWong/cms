package com.hsinwong.cms.data.repository;

import com.hsinwong.cms.data.bean.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Override
    @RestResource(exported = false)
    void delete(User entity);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);
}
