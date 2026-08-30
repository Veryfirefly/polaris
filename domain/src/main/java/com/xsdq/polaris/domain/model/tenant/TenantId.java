package com.xsdq.polaris.domain.model.tenant;

import java.util.Objects;

public record TenantId(long value) {

  public TenantId {
    if (value < 0) throw new IllegalArgumentException("非法的租户id");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    TenantId tenantId = (TenantId) o;
    return value == tenantId.value;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  public static TenantId of(long id) {
      return new TenantId(id);
  }
}
