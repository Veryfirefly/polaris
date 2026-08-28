package com.xsdq.polaris.domain.model.role;

import java.util.Objects;

public record RoleEntity(String entity) {

  public RoleEntity {
    if (entity == null || entity.isBlank()) throw new IllegalArgumentException("角色实体不能为空");
  }

  public static RoleEntity of(String entity) {
    return new RoleEntity(entity);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    RoleEntity that = (RoleEntity) o;
    return Objects.equals(entity, that.entity);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(entity);
  }
}
