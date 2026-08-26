package com.xsdq.polaris.domain.account.model.tenant;

import java.util.Objects;

public record TenantId(long id) {

  public TenantId {
    if (id < 0) throw new IllegalArgumentException("非法的租户id");
  }

  public static TenantId of(long id) {
    return new TenantId(id);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    TenantId tenantId = (TenantId) o;
    return id == tenantId.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
