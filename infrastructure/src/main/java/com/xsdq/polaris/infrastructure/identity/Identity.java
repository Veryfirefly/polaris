package com.xsdq.polaris.infrastructure.identity;

import java.util.Objects;

public record Identity<T>(T value) {

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Identity<?> identity = (Identity<?>) o;
    return Objects.equals(value, identity.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  public static <T> Identity<T> create(T value) {
    return new Identity<>(value);
  }
}
