package com.xsdq.polaris.domain.account.model.menu;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Set;

/**
 * @author XiaoYu
 * @since 2026/8/19 13:22
 */
public record BackendURI(URI uri, String method) {

  private static final Set<String> REQUEST_METHODS =
      Set.of("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE");

  public BackendURI {
    if (uri == null) throw new IllegalArgumentException("URI不能为空");

    String uppercaseMethod = method.toUpperCase();
    if (!REQUEST_METHODS.contains(uppercaseMethod))
      throw new IllegalArgumentException("非法的HTTP请求方法: %s".formatted(method));
  }

  @Override
  public String method() {
    return method.toUpperCase();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    BackendURI that = (BackendURI) o;
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

  public static BackendURI of(String uri, String method) {
    try {
      return of(new URI(uri), method);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("非法的URI: %s".formatted(uri), e);
    }
  }

  public static BackendURI of(URI uri, String method) {
    return new BackendURI(uri, method);
  }
}
