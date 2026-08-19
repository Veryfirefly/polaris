package com.xsdq.polaris.account.domain.model.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author XiaoYu
 * @since 2026/8/19 13:22
 */
public record ApiRequestURI(URI uri, String method) {

	private static final Set<String> REQUEST_METHODS = Set.of("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE",
			"OPTIONS", "TRACE");

	public ApiRequestURI {
		if (uri == null)
			throw new IllegalArgumentException("URI不能为空");

		String upperCaseMethod = method.toUpperCase();
		if (!REQUEST_METHODS.contains(upperCaseMethod))
			throw new IllegalArgumentException("不支持的http请求方法");
	}

	@Override
	public String method() {
		return method.toUpperCase();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ApiRequestURI that = (ApiRequestURI) o;
		return Objects.equals(uri, that.uri) && Objects.equals(method, that.method);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uri, method);
	}

	@Override
	public String toString() {
		return method + " " + uri;
	}

	public static ApiRequestURI of(String uri, String method) {
		try {
			return of(new URI(uri), method);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("非法的URI");
		}
	}

	public static ApiRequestURI of(URI uri, String method) {
		return new ApiRequestURI(uri, method);
	}
}
