package com.hsinwong.cms.service;

import com.hsinwong.cms.data.bean.Menu;
import com.hsinwong.cms.data.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuService {

    public static final Long ROOT_ID = 0L;

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

            menus = parentIdSubMenusMap.get(ROOT_ID);
        }

        return menus;
    }

    public Optional<Menu> getFullMenu(Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isPresent() && !ROOT_ID.equals(menu.get().getParentId())) {
            menu.get().setParentMenu(getFullMenu(menu.get().getParentId()).orElse(null));
        }
        return menu;
    }

}
