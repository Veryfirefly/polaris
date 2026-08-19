package com.xsdq.polaris.error;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author XiaoYu
 * @since 2026/6/4 15:55
 */
public class TenantException extends AuthenticationException {

	public TenantException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public TenantException(String msg) {
		super(msg);
	}
}
