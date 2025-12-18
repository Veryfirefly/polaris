package com.xsdq.polaris.security.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "polaris.security")
public class PolarisSecurityProperties {

    private String tokenSalt;
    private Duration tokenExpireDuration;

    public String getTokenSalt() {
        return tokenSalt;
    }

    public void setTokenSalt(String tokenSalt) {
        this.tokenSalt = tokenSalt;
    }

    public Duration getTokenExpireDuration() {
        return tokenExpireDuration;
    }

    public void setTokenExpireDuration(Duration tokenExpireDuration) {
        this.tokenExpireDuration = tokenExpireDuration;
    }
}
