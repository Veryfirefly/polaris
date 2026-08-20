package com.xsdq.polaris.tenant;

public record TenantId(long id) {

  public TenantId {
    if (id < 0) throw new IllegalArgumentException("Illegal tenant id.");
  }
}
