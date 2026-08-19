package com.xsdq.polaris.account.domain.model.role;

public enum RoleStatus {

	DISABLED((short) 0),
	ENABLED((short) 1),;

	private final short status;

	RoleStatus(short status) {
		this.status = status;
	}

	public short getStatus() {
		return status;
	}
}
