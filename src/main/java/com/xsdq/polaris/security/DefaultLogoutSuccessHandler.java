package com.xsdq.polaris.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsdq.polaris.repository.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    private final JwtTokenService tokenService;
    private final ObjectMapper objectMapper;

	public DefaultLogoutSuccessHandler(JwtTokenService tokenService, ObjectMapper objectMapper) {
		this.tokenService = tokenService;
		this.objectMapper = objectMapper;
	}

	@Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null) {
            PolarisUserDetails userDetails = (PolarisUserDetails) authentication.getPrincipal();
            tokenService.removeUserDetails(userDetails);
        }

        new Response<Void>(HttpStatus.OK.value(), "退出成功")
                .writeToServletResponse(response, HttpStatus.OK, objectMapper);
    }
}
