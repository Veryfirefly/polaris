package com.xsdq.polaris.account.domain.model.role;

import java.util.Objects;

/**
 *
 * @author XiaoYu
 * @since 2026/8/12 11:35
 */
public record PermissionId(long id) {

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		PermissionId that = (PermissionId) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public static PermissionId of(long id) {
		return new PermissionId(id);
	}
}
