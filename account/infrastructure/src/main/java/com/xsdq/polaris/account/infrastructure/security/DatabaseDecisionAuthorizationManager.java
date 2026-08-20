package com.xsdq.polaris.account.infrastructure.security;

import com.xsdq.polaris.account.impl.domain.Permission;
import com.xsdq.polaris.account.impl.service.PermissionService;
import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.CollectionUtils;

/**
 * @author XiaoYu
 * @since 2026/1/9 11:15
 */
public class DatabaseDecisionAuthorizationManager
    implements AuthorizationManager<RequestAuthorizationContext> {

  private static final Logger log =
      LoggerFactory.getLogger(DatabaseDecisionAuthorizationManager.class);

  private final PermissionService permissionService;
  private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

  public DatabaseDecisionAuthorizationManager(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @Nullable
  @Override
  public AuthorizationDecision check(
      Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context) {
    Authentication authentication = authenticationSupplier.get();
    if (!trustResolver.isFullyAuthenticated(authentication)) {
      if (log.isDebugEnabled()) {
        log.debug("Authentication is not fully authenticated {}", authentication);
      }

      return new AuthorizationDecision(false);
    }

    List<Permission> permissions = permissionService.listPermissions();
    if (CollectionUtils.isEmpty(permissions)) {
      log.warn("No permissions found.");
      return new AuthorizationDecision(false);
    }

    for (Permission permission : permissions) {
      if (permission.disabled()) {
        log.debug("The permission {} disabled.", permission);
        continue;
      }

      RequestMatcher requestMatcher = permission.requestMatcher();
      if (requestMatcher.matches(context.getRequest())) {
        GrantedAuthority needAuthority = permission.authority();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
          if (compareGrantedAuthority(needAuthority, authority)) {
            return new AuthorizationDecision(true);
          }
        }

        return new AuthorizationDecision(false);
      }
    }

    if (log.isDebugEnabled()) {
      log.debug(
          "No permissions match, request: [{} {}].",
          context.getRequest().getMethod(),
          context.getRequest().getRequestURI());
    }
    return new AuthorizationDecision(false);
  }

  boolean compareGrantedAuthority(GrantedAuthority needAuthority, GrantedAuthority thatAuthority) {
    return needAuthority.getClass() == thatAuthority.getClass()
        && needAuthority.getAuthority().equals(thatAuthority.getAuthority());
  }
}
