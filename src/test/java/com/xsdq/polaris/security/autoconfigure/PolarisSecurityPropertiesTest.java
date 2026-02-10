package com.xsdq.polaris.security.autoconfigure;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PolarisSecurityPropertiesTest {

	@Autowired
	private PolarisSecurityProperties securityProps;

	@Test
	void testPermitUrls() {
		String[] whitelistUrls = securityProps.whitelistUrls();
		assertNotNull(whitelistUrls);
		assertTrue(whitelistUrls.length > 0);

		for (String url : whitelistUrls) {
			assertTrue(url.startsWith("/"));
		}
	}
}
