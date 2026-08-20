package com.xsdq.polaris.account.domain.model.role;

import java.util.Objects;

public record RoleId(long id) {

  public RoleId {
    if (id < 0) throw new IllegalArgumentException("非法的角色id");
  }

  public static RoleId of(long id) {
    return new RoleId(id);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    RoleId roleId = (RoleId) o;
    return id == roleId.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
