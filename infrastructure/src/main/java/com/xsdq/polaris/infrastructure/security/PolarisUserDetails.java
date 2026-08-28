package com.xsdq.polaris.infrastructure.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xsdq.polaris.infrastructure.cache.SerializeTag;
import com.xsdq.polaris.infrastructure.persistence.UserPO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@Data
public class PolarisUserDetails implements UserDetails, SerializeTag {

  private UserPO user;
  private List<Long> roles;
  private String identifier;
  private String token;
  private LoginDevice device;
  private String ipAddress;
  private long loginTimeMs;
  private long expireTimeMs;
  private List<GrantedAuthority> authorities; // 我在想要不然就不存，

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    /*
       RoleService roleService = ApplicationUtils.getBean(RoleService.class);
       List<RolePO> roles = roleService.getById(new Long[] { 1, 2, 3, 4 }); 对其缓存
       List<GrantedAuthority> authorities = new ArrayList();
       for (RolePO role : roles) {
           if (role.disable()) continue;

           authorities.add(role.toGrantedAuthority());
           authorities.add(role.permissions().toGrantedAuthority());
       }
    */
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
    return true;
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

  public Long tenantId() {
    return user.getTenantId();
  }

  public String account() {
    return user.getAccount();
  }

  @JsonIgnore
  public boolean canRenewal(Duration timeToRefresh) {
    Instant now = Instant.now();
    Instant expireInstant = Instant.ofEpochMilli(expireTimeMs);
    Duration absDiff = Duration.between(now, expireInstant).abs();
    return absDiff.compareTo(timeToRefresh) <= 0;
  }

  public void renewal(Duration timeToLive) {
    Instant newExpireTime = Instant.now().plus(timeToLive);
    setExpireTimeMs(newExpireTime.toEpochMilli());
  }

  public static class Builder {

    private UserPO user;
    private List<Long> roles;
    private LoginDevice device;
    private String ipAddress = EMPTY_STR;
    private long loginTimeMs;
    private long expireTimeMs;
    private List<GrantedAuthority> authorities;

    public Builder() {}

    public Builder user(UserPO user) {
      this.user = user;
      return this;
    }

    public Builder roles(List<Long> roles) {
      this.roles = roles;
      return this;
    }

    public Builder userAgent(Supplier<UserAgent> func) {
      this.device = LoginDevice.create(func.get());
      return this;
    }

    public Builder ipAddress(String ipAddress) {
      this.ipAddress = ipAddress;
      return this;
    }

    public Builder loginTimeMs(long loginTimeMs) {
      this.loginTimeMs = loginTimeMs;
      return this;
    }

    public Builder expireTimeMs(Duration duration) {
      this.expireTimeMs = duration.plusMillis(loginTimeMs).toMillis();
      return this;
    }

    public Builder authorities(List<GrantedAuthority> authorities) {
      this.authorities = authorities;
      return this;
    }

    public PolarisUserDetails build() {
      PolarisUserDetails userDetails = new PolarisUserDetails();
      userDetails.setUser(user);
      userDetails.setRoles(roles);
      userDetails.setDevice(device);
      userDetails.setIpAddress(ipAddress);
      userDetails.setLoginTimeMs(loginTimeMs);
      userDetails.setExpireTimeMs(expireTimeMs);
      userDetails.setAuthorities(authorities);
      return userDetails;
    }
  }
}
