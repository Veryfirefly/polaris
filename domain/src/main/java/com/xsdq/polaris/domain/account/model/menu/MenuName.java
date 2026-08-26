package com.xsdq.polaris.domain.account.model.menu;

import com.xsdq.polaris.common.annotation.Untested;

import java.util.Objects;
import java.util.regex.Pattern;

public record MenuName(String name) {

  @Untested private static final Pattern NAME_REGEX = Pattern.compile("^[a-zA-Z]+$");

  public MenuName {
    if (name == null || name.isBlank()) throw new IllegalArgumentException("菜单名称不能为空");
    if (!NAME_REGEX.matcher(name).matches())
      throw new IllegalArgumentException("菜单名称只能使用大写或小写的英文字母");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    MenuName that = (MenuName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
