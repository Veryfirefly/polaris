package com.xsdq.polaris.domain.policy;

/**
 * @author XiaoYu
 * @since 2026/8/11 19:12
 */
public interface RawPasswordValidatePolicy {

  void validate(String rawPassword) throws IllegalArgumentException;

  static RawPasswordValidatePolicy defaultPolicy() {
    return new DefaultRawPasswordValidatePolicy();
  }
}
