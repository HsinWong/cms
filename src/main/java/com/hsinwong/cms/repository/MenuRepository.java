package com.hsinwong.cms.repository;

import com.hsinwong.cms.bean.Menu;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MenuRepository extends PagingAndSortingRepository<Menu, Long> {

    List<Menu> findByState(Menu.State state);
}
