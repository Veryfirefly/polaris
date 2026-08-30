package com.xsdq.polaris.domain.model.role;

public enum RoleStatus {
  DISABLED((short) 0),
  ENABLED((short) 1);

  private final short status;

  RoleStatus(short status) {
    this.status = status;
  }

  public short getStatus() {
    return status;
  }

  public static RoleStatus of(short status) {
      for (RoleStatus roleStatus : values()) {
          if (roleStatus.getStatus() == status)
              return roleStatus;
      }
      return null;
  }
}
