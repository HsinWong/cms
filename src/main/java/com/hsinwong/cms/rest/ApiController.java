package com.hsinwong.cms.rest;

import com.hsinwong.cms.bean.Menu;
import com.hsinwong.cms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/fullMenus")
    public List<Menu> fullMenus() {
        return menuService.getMenus();
    }
}
