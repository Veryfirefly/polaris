package com.xsdq.polaris.domain.model.user;

import java.util.Objects;

public record UserId(long value) {

  public UserId {
    if (value < 0) throw new IllegalArgumentException("非法的用户id");
  }

  public static UserId of(long id) {
    return new UserId(id);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    UserId userId = (UserId) o;
    return value == userId.value;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }
}
