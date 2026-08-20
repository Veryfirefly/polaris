package com.xsdq.polaris.account.domain.model.user;

public enum UserStatus {
  FROZEN((short) 0),
  NORMAL((short) 1),
  INACTIVE((short) 2);

  private final short status;

  UserStatus(short status) {
    this.status = status;
  }

  public short getStatus() {
    return status;
  }
}
