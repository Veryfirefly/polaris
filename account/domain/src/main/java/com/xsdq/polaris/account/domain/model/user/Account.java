package com.xsdq.polaris.account.domain.model.user;

import java.util.Objects;

public record Account(String value) {

  public Account {
    if (value == null || value.isBlank()) throw new IllegalArgumentException("账号不能为空");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return Objects.equals(value, account.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }
}
