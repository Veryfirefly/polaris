package com.xsdq.polaris.repository;

import com.xsdq.polaris.security.PermissionGrantedAuthority;
import jakarta.annotation.Nonnull;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author XiaoYu
 * @since 2026/1/13 15:26
 */
public record Permission(
    long id, String name, String url, String method, Status status, String permission) {

  public RequestMatcher requestMatcher() {
    return PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.valueOf(method), url);
  }

  public GrantedAuthority authority() {
    return new PermissionGrantedAuthority(name, permission);
  }

  public boolean enable() {
    return status == Status.ENABLED;
  }

  @Nonnull
  @Override
  public String toString() {
    return "(id:%d, name:%s, request:[%s %s], entity:[%s])"
        .formatted(id, name, method, url, permission);
  }
}
