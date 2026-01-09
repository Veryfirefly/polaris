package com.xsdq.polaris.repository.vo;

import lombok.Data;

import org.springframework.validation.annotation.Validated;

/**
 *
 * @author XiaoYu
 * @since 2026/1/9 15:54
 */
@Data
@Validated
public class LoginRequest {

	private String account;
	private String password;
}
