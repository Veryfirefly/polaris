package com.xsdq.polaris.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xsdq.polaris.repository.Permission;
import com.xsdq.polaris.repository.dao.MenuDao;
import com.xsdq.polaris.repository.po.MenuPO;
import com.xsdq.polaris.repository.vo.MenuRouter;
import com.xsdq.polaris.service.MenuService;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author XiaoYu
 * @since 2026/1/13 14:48
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuPO> implements MenuService {

	@Override
	public List<Permission> listPermissions() {
		List<MenuPO> menus = list();
		List<Permission> permissions = new ArrayList<>();

		for (MenuPO menu : menus) {
			if (menu.isButton())
				permissions.add(menu.createPermission());
		}

		return permissions;
	}

	@Override
	public List<MenuRouter> findMenuRouters(List<Long> list) {
		if (CollectionUtils.isEmpty(list))
			throw new IllegalArgumentException();

		List<MenuPO> menus = baseMapper.findMenusByRoleId(list);
		List<MenuRouter> routers = new ArrayList<>();

		Map<Long, MenuRouter> parentMenuMap = menus.stream()
				.filter(MenuPO::isDirOrMenu)
				.collect(Collectors.toMap(MenuPO::getId, MenuRouter::create));

		for (MenuPO menu : menus) {
			if (menu.isDirOrMenu())
				continue;

			MenuRouter parent = parentMenuMap.get(menu.getParentId());
			if (parent != null)
				parent.metadata().addPermission(menu.getPermission());
		}

		for (MenuPO menu : menus) {
			if (menu.isButton())
				continue;

			MenuRouter route = parentMenuMap.get(menu.getId());
			if (menu.isTopLevel()) {
				routers.add(route);
			} else {
				MenuRouter parent = parentMenuMap.get(menu.getParentId());
				if (parent != null)
					parent.addChildren(route);
			}
		}

		routers.sort((o1, o2) -> Integer.compare(o2.order(), o1.order()));
		routers.forEach(MenuRouter::sortChildren);

		routers.forEach(MenuRouter::filterDisabledChildren);
		routers.removeIf(router -> !router.enable());

		return routers;
	}

}
