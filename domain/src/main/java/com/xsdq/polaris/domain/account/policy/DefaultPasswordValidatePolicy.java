package com.xsdq.polaris.domain.account.policy;

import com.xsdq.polaris.common.annotation.Untested;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码合法性验证策略
 *
 * @author XiaoYu
 * @since 2026/8/12 15:37
 */
class DefaultPasswordValidatePolicy implements PasswordValidatePolicy {

  @Untested("We haven't yet made specific plans for when to test.")
  private static final String PASSWORD_VALIDATE_REGEX =
      "^(?=.*[a-zA-Z])(?=.*(\\d|[!@#$%^&*()_+\\-=.,?]))[a-zA-Z\\d!@#$%^&*()_+\\-=.,?]{6,16}$";

  private static final int PASSWORD_MIN_LENGTH = 6;
  private static final int PASSWORD_MAX_LENGTH = 16;

  private final Pattern pattern;

  public DefaultPasswordValidatePolicy() {
    this.pattern = Pattern.compile(PASSWORD_VALIDATE_REGEX);
  }

  @Override
  public void validate(String password) throws IllegalArgumentException {
    if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH)
      throw new IllegalArgumentException("密码字符长度应在6到16位之间");

    Matcher matcher = pattern.matcher(password);
    if (!matcher.matches()) throw new IllegalArgumentException("密码字符应包含英文字符与数字以及特殊字符");
  }
}
