package com.xsdq.polaris.security;

import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface JwtTokenResolver {

    String resolveToken(HttpServletRequest request);
}
