package com.xsdq.polaris.account.domain.model.menu;

import java.util.Objects;

public record MenuId(long id) {

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    MenuId that = (MenuId) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  public static MenuId of(long id) {
    return new MenuId(id);
  }
}
