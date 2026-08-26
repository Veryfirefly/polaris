package com.xsdq.polaris.infrastructure.security.autoconfigure;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "polaris.security")
public class PolarisSecurityProperties {

  private Token token;
  private Set<String> permitUrls;
  private String logoutUrl;

  public String[] whitelistUrls() {
    return getPermitUrls().toArray(new String[0]);
  }

  public RequestMatcher logoutRequestMatcher() {
    return PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, logoutUrl);
  }

  @Data
  public static class Token {

    private String signingKey;
    private Duration timeToLive;
    private Duration timeToRefresh;
    private String headerName;

    public SecretKey signingKey() {
      return Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
    }
  }
}
