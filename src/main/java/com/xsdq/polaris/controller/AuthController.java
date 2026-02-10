package com.xsdq.polaris.controller;

import com.xsdq.polaris.repository.vo.LoginRequest;
import com.xsdq.polaris.security.JwtTokenService;
import com.xsdq.polaris.security.PolarisUserDetails;
import com.xsdq.polaris.service.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private final JwtTokenService tokenService;
	private final AuthenticationManager authenticationManager;

	public AuthController(JwtTokenService tokenService, AuthenticationManager authenticationManager) {
		this.tokenService = tokenService;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				request.getAccount(), request.getPassword());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		PolarisUserDetails userDetails = (PolarisUserDetails) authentication.getPrincipal();
		return tokenService.createToken(userDetails);
	}
}
