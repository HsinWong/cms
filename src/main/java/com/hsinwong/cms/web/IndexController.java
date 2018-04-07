package com.hsinwong.cms.web;

import com.hsinwong.cms.bean.Menu;
import com.hsinwong.cms.repository.MenuRepository;
import com.hsinwong.cms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Menu> menus = menuService.getMenus();
        model.addAttribute("menus", menus);

        return "index";
    }
}
