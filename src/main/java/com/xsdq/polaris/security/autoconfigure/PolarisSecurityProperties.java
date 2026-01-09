package com.xsdq.polaris.security.autoconfigure;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Set;

import io.jsonwebtoken.security.Keys;
import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "polaris.security")
public class PolarisSecurityProperties {

    private Token token;
    private Set<String> permitUrls;
    private String logoutUrl;

    public String[] permitUrls() {
        return getPermitUrls().toArray(new String[0]);
    }

    @Data
    public static class Token {
        private String signingKey;
        private Duration expireDuration;
        private Duration refreshWindowTime;

        public Key signingKey() {
            return Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        }
    }
}
