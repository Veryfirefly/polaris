package com.xsdq.polaris.security;

import jakarta.annotation.Nonnull;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

/**
 *
 * @author XiaoYu
 * @since 2025/12/30 10:04
 */
public class DefaultAuthenticationSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

	private final ApplicationEventPublisher eventPublisher;

	public DefaultAuthenticationSuccessHandler(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public void onApplicationEvent(@Nonnull AuthenticationSuccessEvent event) {

	}
}
