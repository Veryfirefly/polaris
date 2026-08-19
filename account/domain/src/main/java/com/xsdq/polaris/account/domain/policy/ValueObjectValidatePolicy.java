package com.xsdq.polaris.account.domain.policy;

/**
 *
 * @author XiaoYu
 * @since 2026/8/19 14:20
 */
public interface ValueObjectValidatePolicy<T> {

	void validate(T value) throws Exception;
}
