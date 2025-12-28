package com.xsdq.polaris.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public class PolarisPermissionGrantedAuthority implements GrantedAuthority {

    private final String authorityName;
    private final String authorityEntity;

    public PolarisPermissionGrantedAuthority(String authorityName, String authorityEntity) {
        this.authorityName = authorityName;
        this.authorityEntity = authorityEntity;
    }

    @Override
    public String getAuthority() {
        return authorityEntity;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PolarisPermissionGrantedAuthority that = (PolarisPermissionGrantedAuthority) o;
        return Objects.equals(authorityName, that.authorityName) && Objects.equals(authorityEntity, that.authorityEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorityName, authorityEntity);
    }
}
