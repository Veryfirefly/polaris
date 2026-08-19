package com.xsdq.polaris.account.domain.model.resource;

import java.util.Objects;
import java.util.regex.Pattern;

import com.xsdq.polaris.common.annotation.Untested;

public record ResourceName(String name) {

	@Untested
	private static final Pattern NAME_REGEX = Pattern.compile("^[a-zA-Z]+$");

	public ResourceName {
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("菜单名称不能为空");
		if (!NAME_REGEX.matcher(name).matches())
			throw new IllegalArgumentException("菜单名称只能使用大写或小写的英文字母");
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ResourceName that = (ResourceName) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}
}
