package com.xsdq.polaris.http.whois;

import com.xsdq.polaris.error.PolarisRuntimeException;

/**
 * @author XiaoYu
 * @since 2026/6/30 10:31
 */
public class WhoisInfoException extends PolarisRuntimeException {

  public WhoisInfoException() {}

  public WhoisInfoException(String message) {
    super(message);
  }

  public WhoisInfoException(String message, Throwable cause) {
    super(message, cause);
  }

  public WhoisInfoException(Throwable cause) {
    super(cause);
  }
}
