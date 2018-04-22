package com.hsinwong.cms.web;

import com.hsinwong.cms.data.bean.Menu;
import com.hsinwong.cms.service.MenuService;
import com.hsinwong.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @GetMapping("/fullMenus")
    public List<Menu> fullMenus() {
        return menuService.getMenus();
    }

    @PostMapping("/modifyPassword")
    public void modifyPassword(@RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword,
                               HttpServletResponse response) throws IOException {
        if (userService.verifyPassword(oldPassword)) {
            userService.modifyPassword(newPassword);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "原密码不正确，无法修改密码");
        }
    }
}
