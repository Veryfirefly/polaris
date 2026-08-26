package com.xsdq.polaris.infrastructure.security;

import com.xsdq.polaris.domain.account.model.tenant.TenantId;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Token service with user login.
 *
 * @author XiaoYu
 * @since 2026/5/13 16:21
 */
public interface TokenManager<T extends UserDetails> {

  /**
   * Retrieve the corresponding credentials using the access token.
   *
   * @param token access-token
   * @param <T> Any implementation of UserDetails
   * @return credentials
   */
  T getUserDetails(String token) throws JwtException;

  /**
   * Retrieve the corresponding credentials using the access token.
   *
   * @param request http servlet request
   * @param <T> Any implementation of UserDetails
   * @return credentials
   */
  T getUserDetails(HttpServletRequest request) throws JwtException;

  /**
   * Generate an access token using credentials.
   *
   * @param userDetails Logged-in Credential Information
   * @param <T> Any implementation of UserDetails
   * @return access-token
   * @throws JwtException potential credential exceptions
   */
  GeneratedToken createToken(T userDetails) throws JwtException;

  /**
   * Returns the logged-in credential information associated with the tenant. <strong> Key
   * information requires anonymization.</strong>
   *
   * @return Logged-in Credential Information for the Tenant
   * @param tenantId Tenant Information
   * @throws JwtException potential credential exceptions
   */
  Collection<T> listUserDetails(TenantId tenantId) throws JwtException;

  /**
   * Removes an existing credential using an access token; if the credential does not exist, no code
   * is taken.
   *
   * @param userDetails Logged-in Credential Information for the Tenant
   * @throws JwtException potential credential exceptions
   */
  void removeUserDetails(T userDetails) throws JwtException;

  /**
   * Renews the validity of a credential. If the credential's expiration date falls within a
   * specified future timeframe, the associated Access Token is renewed; otherwise, it is considered
   * expired.
   *
   * @param userDetails Logged-in Credential Information for the Tenant
   * @return renewal access-token
   * @throws JwtException potential credential exceptions
   */
  T renewalToken(T userDetails) throws JwtException;
}
