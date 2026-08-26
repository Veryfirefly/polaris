package com.xsdq.polaris.infrastructure.security;


import org.springframework.security.core.AuthenticationException;

public class TenantException extends AuthenticationException {

    public TenantException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TenantException(String msg) {
        super(msg);
    }
}
