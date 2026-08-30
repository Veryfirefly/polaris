package com.xsdq.polaris.domain.model.menu;

import java.util.Objects;

public record MenuId(long value) {

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    MenuId that = (MenuId) o;
    return value == that.value;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  public static MenuId of(long id) {
    return new MenuId(id);
  }
}
