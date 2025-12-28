package com.xsdq.polaris.security;

import com.xsdq.polaris.repository.Status;
import com.xsdq.polaris.repository.dao.UserDao;
import com.xsdq.polaris.repository.po.RolePO;
import com.xsdq.polaris.repository.po.TenantPO;
import com.xsdq.polaris.repository.po.UserPO;
import com.xsdq.polaris.service.TenantService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PolarisUserDetailsService implements UserDetailsService {

    private final UserDao userDao;
    private final TenantService tenantService;

    public PolarisUserDetailsService(UserDao userDao, TenantService tenantService) {
        this.userDao = userDao;
        this.tenantService = tenantService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPO user = userDao.findByAccount(username);
        if (user == null)
            throw new UsernameNotFoundException("Username or password incorrect");

        TenantPO tenant = tenantService.findTenantById(user.getTenantId());
        if (tenant == null)
            throw new UsernameNotFoundException("Tenant not found");
        if (tenant.getStatus() == Status.DISABLED)
            throw new UsernameNotFoundException("Tenant disabled");

        for (RolePO role : user.getRoles()) {
            SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority(role.getName());

        }
        return null;
    }
}
