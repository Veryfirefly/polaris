package com.xsdq.polaris.security;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsdq.polaris.repository.Response;
import com.xsdq.polaris.util.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Access denied handler.
 *
 * @author XiaoYu
 * @since 2025/12/23 17:24
 */
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger log = LoggerFactory.getLogger(DefaultAccessDeniedHandler.class);

	private final ObjectMapper objectMapper;

	public DefaultAccessDeniedHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException exception) throws IOException, ServletException {
		log.warn("Access denied [{}]: '{} {}'.", Utils.getClientIpByHeader(request), request.getMethod(),
				request.getRequestURI());

		new Response<Void>(HttpStatus.FORBIDDEN.value(), "您无权访问该资源!")
				.writeToServletResponse(response, HttpStatus.FORBIDDEN, objectMapper);
	}
}
