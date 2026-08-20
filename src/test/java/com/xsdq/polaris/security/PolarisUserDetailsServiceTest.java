package com.xsdq.polaris.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
class PolarisUserDetailsServiceTest {

  @Autowired private UserDetailsService userDetailsService;

  @Test
  void testLoadUserByUsername() {
    UserDetails userDetails = userDetailsService.loadUserByUsername("xiaoyu");
    assertAll(
        () -> assertNotNull(userDetails), () -> assertEquals("xiaoyu", userDetails.getUsername()));
  }
}
