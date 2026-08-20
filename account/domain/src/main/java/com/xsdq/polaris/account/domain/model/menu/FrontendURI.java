package com.xsdq.polaris.account.domain.model.menu;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public record FrontendURI(URI uri) {

	public FrontendURI {
		if (uri == null)
			throw new IllegalArgumentException("前端uri不能为空");
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		FrontendURI that = (FrontendURI) o;
		return Objects.equals(uri, that.uri);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(uri);
	}

	public static FrontendURI create(URI uri) {
		return new FrontendURI(uri);
	}

	public static FrontendURI create(String uri) {
		try {
			return new FrontendURI(new URI(uri));
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("非法的URI: %s".formatted(uri), e);
		}
	}
}
