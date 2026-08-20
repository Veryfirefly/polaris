package com.xsdq.polaris.account.domain.model.menu;

public enum MenuType {

	DIRECTORY((short) 0),
	MENU((short) 1),
	API((short) 2);

	private final short type;

	MenuType(short type) {
		this.type = type;
	}

	public short getType() {
		return type;
	}
}
