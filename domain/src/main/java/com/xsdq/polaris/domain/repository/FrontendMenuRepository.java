package com.xsdq.polaris.domain.repository;

import com.xsdq.polaris.domain.model.menu.FrontendMenu;
import com.xsdq.polaris.domain.model.menu.MenuId;
import com.xsdq.polaris.domain.model.role.RoleId;

import java.util.List;

public interface FrontendMenuRepository {

    List<FrontendMenu> findAll();

    List<FrontendMenu> findAllByRoleId(Iterable<RoleId> roleIds);

    FrontendMenu save(FrontendMenu frontendMenu);

    void deleteById(MenuId menuId);

    void deleteByIterable(Iterable<MenuId> menuIds);
}
