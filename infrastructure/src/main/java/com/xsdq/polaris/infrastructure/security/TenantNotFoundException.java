package com.xsdq.polaris.infrastructure.security;


import org.springframework.security.core.AuthenticationException;

public class TenantNotFoundException extends AuthenticationException {

    public TenantNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TenantNotFoundException(String msg) {
        super(msg);
    }
}
