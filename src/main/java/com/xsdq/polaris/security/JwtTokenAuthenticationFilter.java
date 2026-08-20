package com.xsdq.polaris.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author XiaoYu
 * @since 2025/12/23 17:21
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

  private final TokenManager<PolarisUserDetails> tokenManager;

  public JwtTokenAuthenticationFilter(TokenManager<PolarisUserDetails> tokenManager) {
    this.tokenManager = tokenManager;
  }

  @Override
  protected void doFilterInternal(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull FilterChain filterChain)
      throws ServletException, IOException {
    PolarisUserDetails userDetails = tokenManager.getUserDetails(request);
    if (userDetails != null && notAuthenticatedWithSecurityContext()) {
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  private boolean notAuthenticatedWithSecurityContext() {
    return SecurityContextHolder.getContext().getAuthentication() == null;
  }
}
