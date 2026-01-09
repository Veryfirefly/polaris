package com.xsdq.polaris.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.xsdq.polaris.security.autoconfigure.PolarisSecurityProperties;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final PolarisSecurityProperties securityProperties;
    private final RedisTemplate<String, Serializable> redisTemplate;
	private final BiFunction<PolarisUserDetails, String, String> cacheKeyFunction =
			(userDetails, identifier) -> "polaris:%d:%s".formatted(userDetails.tenantId().id(), identifier);
	private final Supplier<String> identifierSupplier = () -> UUID.randomUUID().toString();

	public JwtTokenService(
            PolarisSecurityProperties securityProperties,
            RedisTemplate<String, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.securityProperties = securityProperties;
	}

	public <T extends UserDetails> String createToken(T userDetails) {
        PolarisUserDetails customUserDetails = (PolarisUserDetails) userDetails;
        String identifier = identifierSupplier.get();

        customUserDetails.setIdentifier(identifier);

        String token = Jwts.builder()
				.subject(identifier)
				.claims(Map.of("tid", customUserDetails.tenantId().id()))
				.signWith(securityProperties.getToken().signingKey())
				.compact();

		customUserDetails.setToken(token);

		redisTemplate.opsForValue().set(
				cacheKeyFunction.apply(customUserDetails, identifier),
				customUserDetails,
				securityProperties.getToken().getExpireDuration()
		);

		return token;
    }

    public <T extends UserDetails> T getUserDetails(String identity) {
        return null;
    }

    public <T extends UserDetails> T getUserDetails(HttpServletRequest request) {
        return null;
    }

    public <T extends UserDetails> List<T> listUserDetails() {
        return null;
    }

    public void removeUserDetails(String identity) {
    }
}
