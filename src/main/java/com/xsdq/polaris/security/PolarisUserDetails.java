package com.xsdq.polaris.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xsdq.polaris.repository.Status;
import com.xsdq.polaris.repository.po.UserPO;

public class PolarisUserDetails implements UserDetails {

    private final UserPO user;

    @JsonCreator
	public PolarisUserDetails(UserPO user) {
		this.user = user;
	}

    @JsonIgnore
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
        return UserDetails.super.isAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return user.getStatus() == Status.ENABLED;
    }

    public UserPO getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PolarisUserDetails that = (PolarisUserDetails) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user);
    }
}
