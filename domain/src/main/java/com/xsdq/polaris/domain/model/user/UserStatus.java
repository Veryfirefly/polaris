package com.xsdq.polaris.domain.model.user;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
  FROZEN((short) 0),
  NORMAL((short) 1),
  INACTIVE((short) 2);

  private static final Map<Short, UserStatus> CACHE_MAP;

  static {
    CACHE_MAP = new HashMap<>();
    for (UserStatus status : values()) {
      CACHE_MAP.put(status.getStatus(), status);
    }
  }

  private final short status;

  UserStatus(short status) {
    this.status = status;
  }

  public short getStatus() {
    return status;
  }

  public static UserStatus of(short status) {
    return CACHE_MAP.get(status);
  }
}
