package com.xsdq.polaris.infrastructure.identity;

import com.xsdq.polaris.common.errors.PolarisRuntimeException;

public class IdentityGenerateException extends PolarisRuntimeException {

  public IdentityGenerateException() {}

  public IdentityGenerateException(String message) {
    super(message);
  }

  public IdentityGenerateException(String message, Throwable cause) {
    super(message, cause);
  }

  public IdentityGenerateException(Throwable cause) {
    super(cause);
  }
}
