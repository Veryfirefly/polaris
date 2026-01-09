package com.xsdq.polaris.service.impl;

import com.xsdq.polaris.repository.dao.UserDao;
import com.xsdq.polaris.repository.po.UserPO;
import com.xsdq.polaris.security.JwtTokenService;
import com.xsdq.polaris.security.PolarisUserDetails;
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
	private final JwtTokenService tokenService;

	public UserServiceImpl(
			UserDao userDao,
			@Lazy AuthenticationManager authenticationManager,
			JwtTokenService tokenService) {
		this.userDao = userDao;
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	@Override
	public String login(String account, String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account, password);
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		PolarisUserDetails userDetails = (PolarisUserDetails) authentication.getPrincipal();

		return tokenService.createToken(userDetails);
	}

	@Override
	public UserPO getUserByAccount(String account) {
		return userDao.findByAccount(account);
	}
}
