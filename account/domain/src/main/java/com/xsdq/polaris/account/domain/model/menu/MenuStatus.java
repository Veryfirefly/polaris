package com.xsdq.polaris.account.domain.model.resource;

public enum ResourceStatus {
  DISABLED((short) 0),
  ENABLED((short) 1);

  private final short status;

  ResourceStatus(short status) {
    this.status = status;
  }

  public short getStatus() {
    return status;
  }
}
