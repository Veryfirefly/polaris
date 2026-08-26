package com.xsdq.polaris.infrastructure.security;

import com.xsdq.polaris.infrastructure.persistence.account.RolePO;
import com.xsdq.polaris.infrastructure.persistence.account.TenantPO;
import com.xsdq.polaris.infrastructure.persistence.account.UserPO;
import com.xsdq.polaris.infrastructure.security.autoconfigure.PolarisSecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PolarisUserDetailsService implements UserDetailsService {

  private final Logger log = LoggerFactory.getLogger(PolarisUserDetailsService.class);
  private final UserService userService;
  private final TenantService tenantService;
  private final RoleService roleService;
  private final PolarisSecurityProperties securityProperties;

  public PolarisUserDetailsService(
      UserService userService,
      TenantService tenantService,
      RoleService roleService,
      PolarisSecurityProperties securityProperties) {
    this.userService = userService;
    this.tenantService = tenantService;
    this.roleService = roleService;
    this.securityProperties = securityProperties;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserPO user = userService.getUserByAccount(username);
    if (user == null) {
      // This exception is never thrown because the parameter hideUserNotFoundExceptions is set to
      // true
      // in AbstractUserDetailsAuthenticationProvider.
      throw new UsernameNotFoundException("当前用户不存在");
    }

    TenantPO tenant = tenantService.getTenantById(user.getTenantId());
    if (tenant == null) {
      log.error("该用户'{}'所属的租户'{}'不存在, 或已删除.", username, user.getTenantId());
      throw new TenantException("当前租户不存在");
    }

    if (!tenant.enabled()) throw new TenantException("该用户所属的租户已冻结.");

    List<RolePO> roles = roleService.getRolesByUserId(user.getId());
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (RolePO role : roles) {
      if (!role.enable()) {
        log.trace(
            "The user '{}' has had the '{}' role permission disabled.", username, role.getEntity());
        continue;
      }

      for (Permission permission : role.permissions()) {
        authorities.add(permission.authority());
      }
    }

    return new PolarisUserDetails.Builder()
        .user(user)
        .roles(roles.stream().map(RolePO::getId).toList())
        .userAgent(Utils::getCurrentUserAgent)
        .ipAddress(Utils.currentClientIp())
        .loginTimeMs(Instant.now().toEpochMilli())
        .expireTimeMs(securityProperties.getToken().getTimeToLive())
        .authorities(authorities)
        .build();
  }
}
