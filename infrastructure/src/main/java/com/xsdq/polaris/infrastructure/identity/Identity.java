package com.xsdq.polaris.infrastructure.identity;

import java.util.Objects;

/**
 * Identity
 *
 * @author XiaoYu
 * @since 2026/8/19 17:42
 */
public record Identity<T>(T value) {

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Identity<?> identity = (Identity<?>) o;
		return Objects.equals(value, identity.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}
}
