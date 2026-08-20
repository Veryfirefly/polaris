package com.xsdq.polaris.account.domain.model.resource;

import java.util.Objects;

public record ResourceId(long id) {

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ResourceId that = (ResourceId) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  public static ResourceId of(long id) {
    return new ResourceId(id);
  }
}
