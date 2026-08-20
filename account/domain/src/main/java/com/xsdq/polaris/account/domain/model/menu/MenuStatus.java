package com.xsdq.polaris.account.domain.model.menu;

public enum MenuStatus {
  DISABLED((short) 0),
  ENABLED((short) 1);

  private final short status;

  MenuStatus(short status) {
    this.status = status;
  }

  public short getStatus() {
    return status;
  }
}
