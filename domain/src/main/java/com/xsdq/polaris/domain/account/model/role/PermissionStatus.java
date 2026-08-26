package com.xsdq.polaris.domain.account.model.role;

public enum PermissionStatus {
  DISABLED((short) 0),
  ENABLED((short) 1);

  private final short status;

  PermissionStatus(short status) {
    this.status = status;
  }

  public short getStatus() {
    return status;
  }
}
