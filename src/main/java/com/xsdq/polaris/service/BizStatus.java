package com.xsdq.polaris.service;

import lombok.Getter;

import org.springframework.http.HttpStatus;

/**
 * <ul>
 *     <li>1xxx - 关于业务</li>
 *     <li>2xxx - 关于租户</li>
 *     <li>3xxx - unknown</li>
 *     <li>4xxx - 关于系统</li>
 * </ul>
 */
@Getter
public enum BizStatus {
	OK(1000, HttpStatus.OK, "ok"),
	TENANT_LOCKED(2001, HttpStatus.FORBIDDEN, "tenant locked"),
	TENANT_EXPIRED(2002, HttpStatus.FORBIDDEN, "tenant expired"),
	UNAUTHENTICATED(4001, HttpStatus.UNAUTHORIZED, "unauthenticated"),
	UNAUTHORIZED(4003, HttpStatus.FORBIDDEN,"unauthorized");

	private final int status;
	private final HttpStatus httpStatus;
	private final String message;

	BizStatus(int status, HttpStatus httpStatus, String message) {
		this.status = status;
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
