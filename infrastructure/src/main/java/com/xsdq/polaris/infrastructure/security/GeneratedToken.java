package com.xsdq.polaris.infrastructure.security;

import com.xsdq.polaris.common.errors.PolarisRuntimeException;

public record GeneratedToken(String token, long timeToLiveMs, long tenantId) {

  public GeneratedToken {
    if (token == null || token.isBlank())
      throw new PolarisRuntimeException("token cannot be null or blank");
    if (timeToLiveMs < 0) throw new PolarisRuntimeException("timeToLiveMs cannot be negative");
    if (tenantId < 0) throw new PolarisRuntimeException("tenantId cannot be negative");
  }
}
