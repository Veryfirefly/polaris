package com.xsdq.polaris.repository.vo;

import jakarta.validation.constraints.NotEmpty;
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

	@NotEmpty
	private String account;

	@NotEmpty
	private String password;
}
