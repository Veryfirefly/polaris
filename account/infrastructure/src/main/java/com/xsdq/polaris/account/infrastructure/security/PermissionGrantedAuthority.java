package com.xsdq.polaris.account.infrastructure.security;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;

public class PermissionGrantedAuthority implements GrantedAuthority {

    private final String name;
    private final String action;

    @JsonCreator
    public PermissionGrantedAuthority(
            @JsonProperty("name") String name,
            @JsonProperty("authority") String action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public String getAuthority() {
        return action;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PermissionGrantedAuthority that = (PermissionGrantedAuthority) o;
        return Objects.equals(name, that.name) && Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, action);
    }
}
