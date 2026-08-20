package com.xsdq.polaris.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
class JwtTokenManagerTest {

  @Autowired private TokenManager<PolarisUserDetails> tokenManager;

  @Autowired private UserDetailsService userDetailsService;

  @Test
  void createToken() {
    PolarisUserDetails userDetails =
        (PolarisUserDetails) userDetailsService.loadUserByUsername("xiaoyu");
    assertNotNull(userDetails);

    CreatedToken createdToken = tokenManager.createToken(userDetails);
    assertThat(createdToken.token(), allOf(notNullValue(), startsWith("e")));
  }

  @Test
  void renewalToken() {}

  @Test
  void inferExpire() {
    Duration timeToRefresh = Duration.ofMinutes(30);

    Instant now = Instant.now();
    Instant expireInstant =
        ZonedDateTime.of(2026, 5, 20, 15, 15, 0, 0, ZoneId.systemDefault()).toInstant();
    Duration absBetween = Duration.between(now, expireInstant).abs();
    assertFalse(absBetween.compareTo(timeToRefresh) > 0);
  }
}
