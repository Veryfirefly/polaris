package com.xsdq.polaris.service.impl;

import com.xsdq.polaris.repository.dao.UserDao;
import com.xsdq.polaris.service.UserService;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author XiaoYu
 * @since 2025/12/26 16:27
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final AuthenticationManager authenticationManager;

	public UserServiceImpl(UserDao userDao, @Lazy AuthenticationManager authenticationManager) {
		this.userDao = userDao;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public String login(String account, String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account, password);
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "";
	}
}
