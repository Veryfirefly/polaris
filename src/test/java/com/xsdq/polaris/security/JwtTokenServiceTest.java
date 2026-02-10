package com.xsdq.polaris.security;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JwtTokenServiceTest {

	@Autowired
	private JwtTokenService tokenService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Test
	void createToken() {
		UserDetails userDetails = userDetailsService.loadUserByUsername("xiaoyu");
		assertNotNull(userDetails);

		String token = tokenService.createToken(userDetails);
		assertThat(token, allOf(
				notNullValue(),
				startsWith("e")
		));
	}

	@Test
	void renewalToken() {

	}
}
