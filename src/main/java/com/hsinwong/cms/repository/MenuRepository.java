package com.hsinwong.cms.repository;

import com.hsinwong.cms.bean.Menu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuRepository extends CrudRepository<Menu, Long> {

    List<Menu> findByState(Menu.State state);
}
