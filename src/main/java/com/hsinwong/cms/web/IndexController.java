package com.hsinwong.cms.web;

import com.hsinwong.cms.bean.Menu;
import com.hsinwong.cms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

//@Controller
public class IndexController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Menu> menus = menuService.getMenus();
        model.addAttribute("menus", menus);

        return "index";
    }

    @RequestMapping("/load")
    public String load(@RequestParam Long menuId, Model model) {
        Optional<Menu> menu = menuService.getFullMenu(menuId);
        if (menu.isPresent()) {
            model.addAttribute("menu", menu.get());
            return menu.get().getHref();
        } else {
            return "error";
        }
    }
}
