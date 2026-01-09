package com.xsdq.polaris.security;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xsdq.polaris.http.useragent.UserAgent;
import com.xsdq.polaris.repository.Status;
import com.xsdq.polaris.repository.po.UserPO;
import com.xsdq.polaris.tenant.TenantId;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class PolarisUserDetails implements UserDetails, Serializable {

    private boolean enabledTenant;
    private UserPO user;
    private String identifier;
    private String token;
    private String os;
    private String osVersion;
    private String engine;
    private String engineVersion;
    private String browser;
    private String browserVersion;
    private String platform;
    private String ipAddress;
    private long loginTimeMs;
    private long expireTimeMs;
    private long refreshWindowTime;
    private Set<GrantedAuthority> authorities;

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return user.getAccount();
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
        return user.getStatus() == Status.ENABLED;
    }

    public TenantId tenantId() {
        return new TenantId(user.getTenantId());
    }

    @JsonIgnore
    public boolean isExpiration() {
        // (expire time - now) <= window time
        return (expireTimeMs - Instant.now().toEpochMilli()) <= refreshWindowTime;
    }

    public static class Builder {
        private boolean enabledTenant;
        private UserPO user;
        private String os;
        private String osVersion;
        private String engine;
        private String engineVersion;
        private String browser;
        private String browserVersion;
        private String platform;
        private String ipAddress;
        private long loginTimeMs;
        private long expireTimeMs;
        private long refreshWindowTime;
        private Set<GrantedAuthority> authorities;

        public Builder() {}

        public Builder enabledTenant(boolean enabledTenant) {
            this.enabledTenant = enabledTenant;
            return this;
        }

        public Builder user(UserPO user) {
            this.user = user;
            return this;
        }

        public Builder userAgent(Supplier<UserAgent> func) {
            UserAgent userAgent = func.get();
            this.os = userAgent.getOs().getName();
            this.osVersion = userAgent.getOsVersion();
            this.engine = userAgent.getEngine().getName();
            this.engineVersion = userAgent.getEngineVersion();
            this.browser = userAgent.getBrowser().getName();
            this.browserVersion = userAgent.getVersion();
            this.platform = userAgent.getPlatform().getName();
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public  Builder loginTimeMs(long loginTimeMs) {
            this.loginTimeMs = loginTimeMs;
            return this;
        }

        public Builder calculateExpireTime(Duration duration) {
            return expireTimeMs(loginTimeMs + duration.toMillis());
        }

        public Builder expireTimeMs(long expireTimeMs) {
            this.expireTimeMs = expireTimeMs;
            return this;
        }

        public Builder refreshWindowTime(long refreshWindowTime) {
            this.refreshWindowTime = refreshWindowTime;
            return this;
        }

        public Builder authorities(Set<GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public PolarisUserDetails build() {
            PolarisUserDetails userDetails = new PolarisUserDetails();
            userDetails.setEnabledTenant(enabledTenant);
            userDetails.setUser(user);
            userDetails.setOs(os);
            userDetails.setOsVersion(osVersion);
            userDetails.setBrowser(browser);
            userDetails.setBrowserVersion(browserVersion);
            userDetails.setEngine(engine);
            userDetails.setEngineVersion(engineVersion);
            userDetails.setPlatform(platform);
            userDetails.setIpAddress(ipAddress);
            userDetails.setLoginTimeMs(loginTimeMs);
            userDetails.setExpireTimeMs(expireTimeMs);
            userDetails.setRefreshWindowTime(refreshWindowTime);
            userDetails.setAuthorities(authorities);
            return userDetails;
        }
    }
}
