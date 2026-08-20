package com.xsdq.polaris.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsdq.polaris.error.TenantException;
import com.xsdq.polaris.repository.Response;
import com.xsdq.polaris.util.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationEntryPoint.class);

  private final ObjectMapper objectMapper;

  public DefaultAuthenticationEntryPoint(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    Response<String> bizResponse =
        switch (exception) {
          case BadCredentialsException bce -> Response.forbidden(bce);
          case AccountExpiredException aee -> Response.forbidden(aee);
          case DisabledException de -> Response.forbidden(de);
          case TenantException te -> Response.forbidden(te);
          case InsufficientAuthenticationException iae -> Response.forbidden(iae);
          case AuthenticationCredentialsNotFoundException acnfe -> Response.unauthorized(acnfe);
          case InternalAuthenticationServiceException e -> {
            // DaoAuthenticationProvider在retrieveUser时可能无法获取数据库连接.
            log.warn(
                "An internal authentication service malfunction may prevent the database connection "
                    + "from being obtained when retrieving users.",
                exception);
            yield Response.unauthorized(e);
          }
          default -> {
            log.warn("Uncaught authentication exception.", exception);
            yield Response.unauthorized("认证时发生了无法处理的异常, 请重新登录");
          }
        };

    Utils.writeBizResponse(response, bizResponse, objectMapper);
  }
}
