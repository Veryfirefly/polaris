package com.xsdq.polaris.account.domain.policy;

/**
 *
 * @author XiaoYu
 * @since 2026/8/11 19:12
 */
public interface PasswordValidatePolicy {

	void validate(String password) throws IllegalArgumentException;

	static PasswordValidatePolicy defaultPolicy() {
		return new DefaultPasswordValidatePolicy();
	}
}
