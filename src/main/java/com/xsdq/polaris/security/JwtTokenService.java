package com.xsdq.polaris.security;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

import javax.crypto.SecretKey;

import com.xsdq.polaris.security.autoconfigure.PolarisSecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenService {

	private static final String TENANT_ID_KEY = "tid";
	private static final String ACCOUNT_KEY = "acc";
	private static final String BEARER = "Bearer ";
	// the rule 'polaris:tenant_id:account:uuid'
	private static final String TOKEN_CACHE_KEY_RULE = "polaris:%d:%s:%s";

    private final Logger log = LoggerFactory.getLogger(getClass());
	private final SecretKey secretKey;
	private final String bearerHeaderName;
	private final Duration expireDuration;
	private final Duration refreshWindowDuration;
    private final RedisTemplate<String, Serializable> redisTemplate;
	private final Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString().replace("-", "");

	public JwtTokenService(
            PolarisSecurityProperties securityProperties,
            RedisTemplate<String, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.secretKey = securityProperties.getToken().signingKey();
		this.bearerHeaderName = securityProperties.getToken().getHeaderName();
		this.expireDuration = securityProperties.getToken().getExpireDuration();
		this.refreshWindowDuration = securityProperties.getToken().getRefreshWindowDuration();
	}

	public <T extends UserDetails> String createToken(T userDetails) {
        PolarisUserDetails principal = (PolarisUserDetails) userDetails;
        String uuid = uuidSupplier.get();

		Map<String, Object> claims = Map.of(
				TENANT_ID_KEY, principal.tenantId(),
				ACCOUNT_KEY, principal.getUser().getAccount()
		);

        String token = Jwts.builder()
				.subject(uuid)
				.claims(claims)
				.signWith(secretKey)
				.compact();

		principal.setIdentifier(uuid);
		principal.setToken(token);

		redisTemplate.opsForValue().set(
				buildCacheKey(principal.tenantId(), principal.account(), uuid),
				principal,
				expireDuration
		);

		return token;
    }

	private String buildCacheKey(Long tenantId, String account, String identifier) {
		return TOKEN_CACHE_KEY_RULE.formatted(tenantId, account, identifier);
	}

	@SuppressWarnings("unchecked")
    public <T extends UserDetails> T getUserDetails(String token) {
		Claims claims = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();

		String uuid = claims.getSubject();
		String account = claims.get(ACCOUNT_KEY, String.class);
		Long tenantId = claims.get(TENANT_ID_KEY, Long.class);

		if (uuid == null || account == null || tenantId == null) {
			return null;
		}

		String cacheKey = buildCacheKey(tenantId, account, uuid);
		PolarisUserDetails principal = (PolarisUserDetails) redisTemplate.opsForValue().get(cacheKey);

		if (principal != null && principal.isExpiration(refreshWindowDuration)) {
			principal = (PolarisUserDetails) renewal(cacheKey, principal);
		}

		return (T) principal;
    }

	protected UserDetails renewal(String cacheKey, PolarisUserDetails userDetails) {
		long newExpireTimeMs = Instant.now().toEpochMilli() + expireDuration.toMillis();
		userDetails.setExpireTimeMs(newExpireTimeMs);

		if (log.isDebugEnabled()) {
			log.debug("The {} userDetails are about to expire and have been renewed util [{}].",
					userDetails.getUser().getAccount(), Instant.ofEpochMilli(newExpireTimeMs));
		}

		redisTemplate.opsForValue().set(
				cacheKey,
				userDetails,
				expireDuration
		);

		return (PolarisUserDetails) redisTemplate.opsForValue().get(cacheKey);
	}

    public <T extends UserDetails> T getUserDetails(HttpServletRequest request) {
		String bearerToken = extractAuthorizationBearerToken(request);
		if (!StringUtils.hasText(bearerToken)) {
			// No exceptions are thrown; Spring Security takes over.
			return null;
		}
		return getUserDetails(bearerToken);
    }

    public <T extends UserDetails> List<T> listUserDetails(@Nonnull Long tenantId) {
		// todo
		Set<String> groupedTenantUsers = redisTemplate.keys("polaris:" + tenantId);
		return null;
    }

    public <T extends UserDetails> void removeUserDetails(T userDetails) {
		PolarisUserDetails principal = (PolarisUserDetails) userDetails;
		String cacheKey = buildCacheKey(principal.tenantId(), principal.getUser().getAccount(), principal.getIdentifier());

		if (log.isDebugEnabled()) {
			log.debug("The login cache information for the user '{}' under the tenant '{}' has been deleted.",
					principal.account(), principal.tenantId());
		}

		redisTemplate.delete(cacheKey);
    }

	private String extractAuthorizationBearerToken(HttpServletRequest request) {
		String bearer = request.getHeader(bearerHeaderName);
		if (!StringUtils.hasText(bearer))
			return null;

		bearer = bearer.replace(BEARER, "");
		return bearer;
	}
}
