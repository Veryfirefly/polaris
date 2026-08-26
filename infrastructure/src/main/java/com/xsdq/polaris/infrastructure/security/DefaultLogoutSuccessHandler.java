package com.xsdq.polaris.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.xsdq.polaris.infrastructure.utils.ApplicationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

  private final TokenManager<PolarisUserDetails> tokenManager;
  private final ObjectMapper objectMapper;

  public DefaultLogoutSuccessHandler(
      TokenManager<PolarisUserDetails> tokenManager, ObjectMapper objectMapper) {
    this.tokenManager = tokenManager;
    this.objectMapper = objectMapper;
  }

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    if (authentication != null) {
      PolarisUserDetails userDetails = (PolarisUserDetails) authentication.getPrincipal();
      tokenManager.removeUserDetails(userDetails);

      ApplicationUtils.publishEvent(
          new LoginHistoryEvent(
              userDetails, LoginStatus.LOGGED_OUT, Utils.getClientIpByHeader(request)));
    }

    Utils.writeBizResponse(response, Response.ok("退出成功"), objectMapper);
  }
}
