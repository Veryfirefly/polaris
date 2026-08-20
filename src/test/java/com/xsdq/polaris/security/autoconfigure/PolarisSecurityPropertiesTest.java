package com.xsdq.polaris.security.autoconfigure;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PolarisSecurityPropertiesTest {

  @Autowired private PolarisSecurityProperties securityProps;

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
