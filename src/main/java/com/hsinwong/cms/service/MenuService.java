package com.hsinwong.cms.service;

import com.hsinwong.cms.bean.Menu;
import com.hsinwong.cms.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;


    public List<Menu> getMenus() {
        List<Menu> menus = menuRepository.findByState(Menu.State.NORMAL);

        if (!menus.isEmpty()) {
            Map<Long, List<Menu>> parentIdSubMenusMap = new HashMap<>();
            menus.forEach(menu -> parentIdSubMenusMap.compute(menu.getParentId(), (parentId, subMenus) -> {
                if (subMenus == null) {
                    subMenus = new ArrayList<>();
                }
                subMenus.add(menu);
                return subMenus;
            }));

            parentIdSubMenusMap.forEach((parentId, subMenus) ->
                    subMenus.sort(Comparator.comparingInt(Menu::getOrderNum)));

            menus.forEach(menu -> {
                if (!menu.getLeaf() && parentIdSubMenusMap.containsKey(menu.getId())) {
                    menu.setSubMenus(parentIdSubMenusMap.get(menu.getId()));
                }
            });

            menus = parentIdSubMenusMap.get(0L);
        }

        return menus;
    }

}
