package com.xsdq.polaris.security;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsdq.polaris.repository.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationEntryPoint.class);

    private final ObjectMapper objectMapper;

	public DefaultAuthenticationEntryPoint(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        if (exception instanceof BadCredentialsException badCredentialsException) {
            new Response<Void>(HttpStatus.OK.value(), badCredentialsException.getMessage())
                    .writeToServletResponse(response, HttpStatus.OK, objectMapper);
        } else if (exception instanceof AccountExpiredException accountExpiredException) {
            new Response<Void>(HttpStatus.OK.value(), accountExpiredException.getMessage())
                    .writeToServletResponse(response, HttpStatus.OK, objectMapper);
        } else {
			log.warn("Authenticated failed.", exception);
			new Response<Void>(HttpStatus.UNAUTHORIZED.value(), "您还未授权")
					.writeToServletResponse(response, HttpStatus.UNAUTHORIZED, objectMapper);
		}
    }
}
