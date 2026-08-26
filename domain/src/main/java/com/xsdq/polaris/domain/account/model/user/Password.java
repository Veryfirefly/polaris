package com.xsdq.polaris.domain.account.model.user;

import java.util.Objects;

public record Password(String encodedValue) {

  public Password {
    if (encodedValue == null || encodedValue.isBlank())
      throw new IllegalArgumentException("密码不能为空");
  }

  @Override
  public String toString() {
    return "******";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Password password = (Password) o;
    return Objects.equals(encodedValue, password.encodedValue);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(encodedValue);
  }
}
