package com.xsdq.polaris.domain.model.menu;

public enum MenuType {

	DIRECTORY((short) 0, true, false, true, false),
	MENU((short) 1, true, true, false, true),
	API((short) 2, false, false, false, false);

	private final short type;
	private final boolean hideSelf;
	private final boolean hidePageHeader;
	private final boolean hideChildrenInMenu;
	private final boolean cacheable;

	MenuType(short type, boolean hideSelf, boolean hidePageHeader, boolean hideChildrenInMenu, boolean cacheable) {
		this.type = type;
		this.hideSelf = hideSelf;
		this.hidePageHeader = hidePageHeader;
		this.hideChildrenInMenu = hideChildrenInMenu;
		this.cacheable = cacheable;
	}

	public short type() {
		return type;
	}

	public boolean canHideSelf() {
		return hideSelf;
	}

	public boolean canHidePageHeader() {
		return hidePageHeader;
	}

	public boolean canHideChildrenInMenu() {
		return hideChildrenInMenu;
	}

	public boolean cacheable() {
		return cacheable;
	}

	public static MenuType of(short type) {
		for (MenuType menuType : MenuType.values()) {
			if (menuType.type == type) {
				return menuType;
			}
		}
		return null;
	}
}
