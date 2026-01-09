package com.xsdq.polaris.security;

import java.util.function.Supplier;

import org.springframework.lang.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/**
 *
 * @author XiaoYu
 * @since 2026/1/9 11:15
 */
public class DynamicDecisionAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

	@Nullable
	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
		return new AuthorizationDecision(true);
	}
}
