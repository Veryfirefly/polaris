package com.xsdq.polaris.repository.vo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xsdq.polaris.error.PolarisRuntimeException;
import com.xsdq.polaris.repository.po.MenuPO;

public record MenuRouter(String path,
						 String name,
						 String component,
						 MenuRouterMetadata metadata,
						 String redirect,
						 boolean hidden,
						 boolean hiddenChildrenInMenu,
						 List<MenuRouter> children,
						 @JsonIgnore int order,
						 @JsonIgnore boolean enable) {

	public MenuRouter {
		if (name == null || name.isEmpty())
			throw new IllegalStateException("The MenuRouter name cannot be empty; it is a unique key.");
	}

	public MenuRouter(
			String path,
			String name,
			String component,
			MenuRouterMetadata metadata,
			String redirect,
			boolean hidden,
			boolean hiddenChildrenInMenu,
			int order,
			boolean enable) {
		this(path, name, component, metadata, redirect, hidden, hiddenChildrenInMenu, new ArrayList<>(), order, enable);
	}

	public void addChildren(MenuRouter router) {
		children.add(router);
	}

	public void sortChildren() {
		children.sort((o1, o2) -> Integer.compare(o2.order, o1.order));
	}

	public void filterDisabledChildren() {
		children.removeIf(router -> !router.enable());
	}

	public static MenuRouter create(MenuPO menuPO) {
		if (menuPO == null)
			throw new PolarisRuntimeException("menuPO is null.");

		return new MenuRouter(
				menuPO.getPath(),
				menuPO.getName(),
				menuPO.getComponent(),
				MenuRouterMetadata.create(menuPO),
				menuPO.getRedirect(),
				menuPO.getHidden(),
				menuPO.getHiddenChildren(),
				menuPO.getOrder(),
				menuPO.enabled()
		);
	}

	public record MenuRouterMetadata(String title,
									 String icon,
									 boolean keepAlive,
									 String target,
									 boolean hidden,
									 boolean hiddenHeaderContent,
									 List<String> permission) {

		public MenuRouterMetadata(String title,
				String icon,
				boolean keepAlive,
				String target,
				boolean hidden,
				boolean hiddenHeaderContent) {
			this(title, icon, keepAlive, target, hidden, hiddenHeaderContent, new ArrayList<>());
		}

		public void addPermission(String permission) {
			this.permission.add(permission);
		}

		public static MenuRouterMetadata create(MenuPO menuPO) {
			return new MenuRouterMetadata(
					menuPO.getTitle(),
					menuPO.getIconPath(),
					menuPO.getCacheable(),
					menuPO.getTarget(),
					false,
					menuPO.getHiddenHeader()
			);
		}
	}

}
