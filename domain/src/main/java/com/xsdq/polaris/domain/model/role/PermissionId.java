package com.xsdq.polaris.domain.model.role;

import java.util.Objects;

/**
 * @author XiaoYu
 * @since 2026/8/12 11:35
 */
public record PermissionId(long value) {

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    PermissionId that = (PermissionId) o;
    return value == that.value;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  public static PermissionId of(long id) {
    return new PermissionId(id);
  }
}
