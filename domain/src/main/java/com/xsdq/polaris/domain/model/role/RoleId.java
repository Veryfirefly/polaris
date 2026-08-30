package com.xsdq.polaris.domain.model.role;

import java.util.Objects;

public record RoleId(long value) {

  public RoleId {
    if (value < 0) throw new IllegalArgumentException("非法的角色id");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    RoleId roleId = (RoleId) o;
    return value == roleId.value;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  public static RoleId of(long id) {
      return new RoleId(id);
  }
}
