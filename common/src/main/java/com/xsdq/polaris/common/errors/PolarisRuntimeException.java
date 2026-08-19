package com.xsdq.polaris.common.errors;

/**
 *
 * @author XiaoYu
 * @since 2026/8/11 15:35
 */
public class PolarisRuntimeException extends RuntimeException {

	public PolarisRuntimeException() {
	}

	public PolarisRuntimeException(String message) {
		super(message);
	}

	public PolarisRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PolarisRuntimeException(Throwable cause) {
		super(cause);
	}
}
