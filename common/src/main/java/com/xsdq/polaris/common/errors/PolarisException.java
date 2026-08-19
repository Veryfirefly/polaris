package com.xsdq.polaris.common.errors;

/**
 *
 * @author XiaoYu
 * @since 2026/8/11 15:36
 */
public class PolarisException extends Exception {

	public PolarisException() {
	}

	public PolarisException(String message) {
		super(message);
	}

	public PolarisException(String message, Throwable cause) {
		super(message, cause);
	}

	public PolarisException(Throwable cause) {
		super(cause);
	}
}
