package com.xsdq.polaris.controller;

import com.xsdq.polaris.repository.vo.LoginRequest;
import com.xsdq.polaris.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author XiaoYu
 * @since 2026/1/9 14:25
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequest request) {
		return userService.login(request.getAccount(), request.getPassword());
	}
}
