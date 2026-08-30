package com.xsdq.polaris.domain.model.role;

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

  public static PermissionStatus of(short status) {
      for (PermissionStatus permissionStatus : values()) {
          if (permissionStatus.status == status)
              return permissionStatus;
      }
      return null;
  }
}
