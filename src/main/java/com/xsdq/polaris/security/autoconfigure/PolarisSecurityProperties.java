package com.xsdq.polaris.security.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "polaris.security")
public class PolarisSecurityProperties {

    private String tokenSalt;
    private Duration tokenExpireDuration;
    private Set<String> permitUrls;
    private String logoutUrl;

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

    public Set<String> getPermitUrls() {
        return permitUrls;
    }

    public void setPermitUrls(Set<String> permitUrls) {
        this.permitUrls = permitUrls;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String[] toPermitUrls() {
        return getPermitUrls().toArray(new String[0]);
    }
}
