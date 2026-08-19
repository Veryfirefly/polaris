package com.xsdq.polaris.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author XiaoYu
 * @since 2026/6/2 16:09
 */
public final class AuthenticationUtils {

	public static PolarisUserDetails getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
			throw new AuthenticationCredentialsNotFoundException("用户身份未验证, 请重新登录");

		Object principal = authentication.getPrincipal();
		if (principal instanceof PolarisUserDetails pud) {
			return pud;
		}

		throw new AuthenticationCredentialsNotFoundException("登录凭证已过期, 请重新登录");
	}

	public static String currentUsername() {
		return getUserDetails().getUsername();
	}
}
