package com.xsdq.polaris.domain.model.tenant;

public enum TenantMenuStatus {
    DISABLED((short) 0),
    ENABLED((short) 1);

    private final short status;

    TenantMenuStatus(short status) {
        this.status = status;
    }

    public short status() {
        return status;
    }
}
