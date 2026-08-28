package com.xsdq.polaris.domain.model.user;

import java.util.Objects;
import java.util.regex.Pattern;

import com.xsdq.polaris.common.annotation.Untested;

public record Account(String value) {

  @Untested
  private static final Pattern ACCOUNT_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

  public Account {
    if (value == null || value.isBlank())
      throw new IllegalArgumentException("账号不能为空");
    if (value.length() < 6 || value.length() > 20)
      throw new IllegalArgumentException("账号长度应控制在6-20个字符内");
    if (!ACCOUNT_PATTERN.matcher(value).matches())
      throw new IllegalArgumentException("账号仅支持大小写英文字符及数字");
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

  public static Account of(String value) {
    return new Account(value);
  }
}
