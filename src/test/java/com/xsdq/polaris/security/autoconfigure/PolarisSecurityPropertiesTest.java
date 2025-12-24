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
		var permitUrls = securityProps.toPermitUrls();
		assertNotNull(permitUrls);
		assertTrue(permitUrls.length > 0);

		for (var url : permitUrls) {
			assertTrue(url.startsWith("/"));
		}
	}
}
