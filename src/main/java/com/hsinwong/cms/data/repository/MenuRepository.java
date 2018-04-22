package com.hsinwong.cms.data.repository;

import com.hsinwong.cms.data.bean.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface MenuRepository extends CrudRepository<Menu, Long> {

    List<Menu> findByState(Menu.State state);

    @Override
    @RestResource(exported = false)
    void delete(Menu entity);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);
}
