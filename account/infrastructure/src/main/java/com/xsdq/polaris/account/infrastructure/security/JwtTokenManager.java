package com.xsdq.polaris.account.infrastructure.security;

import com.xsdq.polaris.account.impl.security.TokenManager;
import com.xsdq.polaris.account.impl.security.autoconfigure.PolarisSecurityProperties;
import com.xsdq.polaris.bean.LoginStatus;
import com.xsdq.polaris.bean.event.LoginHistoryEvent;
import com.xsdq.polaris.core.cache.RedisCacheService;
import com.xsdq.polaris.tenant.TenantId;
import com.xsdq.polaris.util.ApplicationUtils;
import com.xsdq.polaris.util.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenManager implements TokenManager<PolarisUserDetails> {

  private static final String ACCESS_TOKEN_PREFIX = "Bearer ";
  private static final String TENANT_ID_KEY = "tid";
  private static final String TENANT_CACHE_KEY_TEMPLATE = "polaris:%d";
  private static final String CACHE_KEY_TEMPLATE = TENANT_CACHE_KEY_TEMPLATE + ":%s";

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final SecretKey secretKey;
  private final Duration timeToLive;
  private final Duration timeToRefresh;
  private final String authHeaderName;
  private final RedisCacheService redisCacheService;
  private RedisTemplate<String, Serializable> redisTemplate;
  private final Supplier<String> createUuidSupplier =
      () -> UUID.randomUUID().toString().replace("-", "");

  public JwtTokenManager(
      PolarisSecurityProperties securityProps, RedisCacheService redisCacheService) {
    this.timeToLive = securityProps.getToken().getTimeToLive();
    this.timeToRefresh = securityProps.getToken().getTimeToRefresh();
    this.secretKey = securityProps.getToken().signingKey();
    this.authHeaderName = securityProps.getToken().getHeaderName();
    this.redisCacheService = redisCacheService;
  }

  @Override
  public PolarisUserDetails getUserDetails(String token) throws JwtException {
    Assert.notNull(token, "Token must not be null");

    Claims claims =
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

    String uuid = claims.getSubject();
    Long tenantId = claims.get(TENANT_ID_KEY, Long.class);

    if (!StringUtils.hasText(uuid) || tenantId == null) throw new JwtException("Invalid token");

    PolarisUserDetails userDetails =
        redisCacheService.get(CACHE_KEY_TEMPLATE.formatted(tenantId, uuid));
    if (userDetails != null) renewalToken(userDetails);

    return userDetails;
  }

  @Override
  public PolarisUserDetails getUserDetails(HttpServletRequest request) throws JwtException {
    String token = getToken(request);
    if (token == null) return null;
    return getUserDetails(token);
  }

  @Override
  public GeneratedToken createToken(PolarisUserDetails userDetails) throws JwtException {
    Assert.notNull(userDetails, "UserDetails must not be null");
    String uuid = createUuidSupplier.get();

    String token =
        Jwts.builder()
            .subject(uuid)
            .claims(Map.of(TENANT_ID_KEY, userDetails.tenantId()))
            .signWith(secretKey)
            .compact();

    userDetails.setIdentifier(uuid);
    userDetails.setToken(token);

    redisCacheService.put(
        CACHE_KEY_TEMPLATE.formatted(userDetails.tenantId(), uuid), userDetails, timeToLive);

    if (log.isDebugEnabled()) {
      log.debug(
          "所属租户'{}-{}'下的用户'{}'创建了jwt token: {}",
          userDetails.tenantId(),
          userDetails.tenantId(),
          userDetails.getUsername(),
          token);
    }

    ApplicationUtils.publishEvent(
        new LoginHistoryEvent(userDetails, LoginStatus.LOGGED_IN, Utils.currentClientIp()));

    return new GeneratedToken(token, timeToLive.toMillis(), userDetails.tenantId());
  }

  @Override
  public Collection<PolarisUserDetails> listUserDetails(TenantId tenantId) throws JwtException {
    return redisCacheService.mutiGet(TENANT_CACHE_KEY_TEMPLATE.formatted(tenantId.id()));
  }

  @Override
  public void removeUserDetails(PolarisUserDetails userDetails) throws JwtException {
    Assert.notNull(userDetails, "UserDetails must not be null");

    redisCacheService.evict(
        CACHE_KEY_TEMPLATE.formatted(userDetails.tenantId(), userDetails.getIdentifier()));
  }

  @Override
  public PolarisUserDetails renewalToken(PolarisUserDetails userDetails) throws JwtException {
    Assert.notNull(userDetails, "UserDetails is null");

    if (userDetails.canRenewal(timeToRefresh)) {
      userDetails.renewal(timeToLive);
      redisCacheService.put(
          CACHE_KEY_TEMPLATE.formatted(userDetails.tenantId(), userDetails.getIdentifier()),
          userDetails,
          timeToLive);

      ApplicationUtils.publishEvent(
          new LoginHistoryEvent(userDetails, LoginStatus.RENEWAL, Utils.currentClientIp()));

      if (log.isDebugEnabled()) {
        log.debug(
            "'{}-{}' token to be renewal to {}",
            userDetails.tenantId(),
            userDetails.getUser(),
            Instant.now().plus(timeToLive));
      }
    }

    return userDetails;
  }

  private String getToken(HttpServletRequest request) {
    String header = request.getHeader(authHeaderName);
    return (StringUtils.hasText(header) && header.startsWith(ACCESS_TOKEN_PREFIX))
        ? header.substring(ACCESS_TOKEN_PREFIX.length())
        : null;
  }
}
