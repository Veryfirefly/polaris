package com.xsdq.polaris.security;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xsdq.polaris.error.PolarisRuntimeException;
import com.xsdq.polaris.http.useragent.UserAgentParser;
import com.xsdq.polaris.repository.po.PermissionPO;
import com.xsdq.polaris.repository.po.RolePO;
import com.xsdq.polaris.repository.po.TenantPO;
import com.xsdq.polaris.repository.po.UserPO;
import com.xsdq.polaris.security.autoconfigure.PolarisSecurityProperties;
import com.xsdq.polaris.service.RoleService;
import com.xsdq.polaris.service.TenantService;
import com.xsdq.polaris.service.UserService;
import com.xsdq.polaris.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        if (user == null)
            throw new UsernameNotFoundException("用户不存在");

        TenantPO tenant = tenantService.getTenantById(user.getTenantId());
        if (tenant == null) {
            log.error("{}的租户id不存在, 租户id: {}", username, user.getTenantId());
            throw new PolarisRuntimeException("租户不存在");
        }

        List<RolePO> roles = roleService.getRolesByUserId(user.getId());
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RolePO role : roles) {
            if (role.disabled()) {
                log.trace("The user '{}' has had the '{}' role permission disabled.", username, role.getEntity());
                continue;
            }

            for (PermissionPO permission : role.getPermissions()) {
                authorities.add(new PolarisPermissionGrantedAuthority(permission.getName(), permission.getPermission()));
            }
            // undefined custom RoleVoter
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getEntity()));
        }

        return new PolarisUserDetails.Builder()
                .enabledTenant(tenant.enabled())
                .user(user)
                .userAgent(() -> UserAgentParser.parse(Utils.getRequest()))
                .loginTimeMs(Instant.now().toEpochMilli())
                .calculateExpireTime(securityProperties.getToken().getExpireDuration())
                .refreshWindowTime(securityProperties.getToken().getRefreshWindowTime().toMillis())
                .authorities(authorities)
                .build();
    }
}
