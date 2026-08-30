package com.xsdq.polaris.infrastructure.repository;

import com.xsdq.polaris.domain.model.menu.BackendMenu;
import com.xsdq.polaris.domain.model.menu.MenuId;
import com.xsdq.polaris.domain.model.role.RoleId;
import com.xsdq.polaris.domain.repository.BackendMenuRepository;
import com.xsdq.polaris.infrastructure.mapper.MenuMapper;
import com.xsdq.polaris.infrastructure.persistence.MenuPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BackendMenuRepositoryImpl implements BackendMenuRepository {

    private final MenuMapper menuMapper;

    public BackendMenuRepositoryImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public Optional<BackendMenu> findById(MenuId menuId) {
        MenuPO menuPO = menuMapper.selectById(menuId.value());
        return Optional.empty();
    }

    @Override
    public List<BackendMenu> findAllByRoleId(Iterable<RoleId> roleIds) {
        return List.of();
    }

    @Override
    public BackendMenu save(BackendMenu backendMenu) {
        return null;
    }

    @Override
    public void deleteById(MenuId menuId) {

    }
}
