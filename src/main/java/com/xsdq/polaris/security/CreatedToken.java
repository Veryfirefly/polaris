package com.xsdq.polaris.security;

import com.xsdq.polaris.error.PolarisRuntimeException;

public record CreatedToken(String token, long timeToLiveMs, long tenantId) {

  public CreatedToken {
    if (token == null || token.isBlank())
      throw new PolarisRuntimeException("token cannot be null or blank");
    if (timeToLiveMs < 0) throw new PolarisRuntimeException("timeToLiveMs cannot be negative");
    if (tenantId < 0) throw new PolarisRuntimeException("tenantId cannot be negative");
  }
}
