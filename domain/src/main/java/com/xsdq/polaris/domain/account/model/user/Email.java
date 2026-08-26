package com.xsdq.polaris.domain.account.model.user;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author XiaoYu
 * @since 2026/8/14 16:43
 */
public record Email(String address) {

  private static final Pattern EMAIL_REGEX =
      Pattern.compile(
          "^[a-zA-Z0-9]+([._%+-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([.-][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,}$");

  public Email {
    if (address == null || address.isBlank()) throw new IllegalArgumentException("邮箱地址不能为空");
    if (!EMAIL_REGEX.matcher(address).matches()) throw new IllegalArgumentException("无效的邮箱地址");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Email email = (Email) o;
    return Objects.equals(address, email.address);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(address);
  }
}
