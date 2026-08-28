package com.xsdq.polaris.domain.model.role;

import java.util.Objects;

/**
 * @author XiaoYu
 * @since 2026/8/17 17:06
 */
public record PermissionCode(String code) {

  public PermissionCode {
    if (code == null || code.isBlank()) throw new IllegalArgumentException("权限行为不能为空");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    PermissionCode that = (PermissionCode) o;
    return Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(code);
  }

  public static PermissionCode of(String code) {
    return new PermissionCode(code);
  }
}
