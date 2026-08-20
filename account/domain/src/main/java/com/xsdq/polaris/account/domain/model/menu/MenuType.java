package com.xsdq.polaris.account.domain.model.resource;

public enum ResourceType {

	DIRECTORY((short) 0),
	MENU((short) 1),
	API((short) 2);

	private final short type;

	ResourceType(short type) {
		this.type = type;
	}

	public short getType() {
		return type;
	}
}
