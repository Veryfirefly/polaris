package com.xsdq.polaris.security;

import com.xsdq.polaris.tenant.TenantId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xsdq.polaris.repository.Status;
import com.xsdq.polaris.repository.po.UserPO;

public class PolarisUserDetails implements UserDetails, Serializable {

    private long tenantId; // no need tenant object, because we cached use another key.
    private boolean enabledTenant;
    private UserPO userHolder;
    private List<GrantedAuthority> authorities;
    private String os;
    private String browser;
    private String ipAddress;
    private long loginTimeMs;
    private long expireTimeMs;
    private long windowTime;

    @JsonIgnore
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userHolder.getPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return userHolder.getAccount();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isEnabledTenant();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return userHolder.getStatus() == Status.ENABLED;
    }

    public UserPO getUserHolder() {
        return userHolder;
    }

    public void setUserHolder(UserPO userHolder) {
        this.userHolder = userHolder;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setEnabledTenant(boolean enabledTenant) {
        this.enabledTenant = enabledTenant;
    }

    public boolean isEnabledTenant() {
        return enabledTenant;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs() {
        return os;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowser() {
        return browser;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setLoginTimeMs(long loginTimeMs) {
        this.loginTimeMs = loginTimeMs;
    }

    public long getLoginTimeMs() {
        return loginTimeMs;
    }

    public void setExpireTimeMs(long expireTimeMs) {
        this.expireTimeMs = expireTimeMs;
    }

    public long getExpireTimeMs() {
        return expireTimeMs;
    }

    public void setWindowTime(long windowTime) {
        this.windowTime = windowTime;
    }

    public long getWindowTime() {
        return windowTime;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public TenantId tenantId() {
        return new TenantId(tenantId);
    }

    public boolean isExpiration() {
        // (expire time - now) <= window time
        return (expireTimeMs - Instant.now().toEpochMilli()) <= windowTime;
    }
}
