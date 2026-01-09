package com.xsdq.polaris.error;

/**
 *
 * @author XiaoYu
 * @since 2025/12/29 16:20
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
