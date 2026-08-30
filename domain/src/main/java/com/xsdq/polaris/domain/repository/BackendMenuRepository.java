package com.xsdq.polaris.domain.repository;

import com.xsdq.polaris.domain.model.menu.BackendMenu;
import com.xsdq.polaris.domain.model.menu.MenuId;
import com.xsdq.polaris.domain.model.role.RoleId;

import java.util.List;
import java.util.Optional;

public interface BackendMenuRepository {

    Optional<BackendMenu> findById(MenuId menuId);

    List<BackendMenu> findAllByRoleId(Iterable<RoleId> roleIds);

    BackendMenu save(BackendMenu backendMenu);

    void deleteById(MenuId menuId);
}
