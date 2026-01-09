package com.xsdq.polaris.serialize;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xsdq.polaris.http.useragent.UserAgentParser;
import com.xsdq.polaris.repository.dao.UserMockDataTestKit;
import com.xsdq.polaris.security.PolarisUserDetails;
import com.xsdq.polaris.servlet.MockServletTestKit;
import com.xsdq.polaris.util.Utils;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

@SpringBootTest
class UserDetailsBuilderSerializeTests extends MockServletTestKit implements UserMockDataTestKit {

	private final Duration expireDuration = Duration.ofHours(1);
	private final Duration refreshWindowTimeDuration = Duration.ofMinutes(30);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void serializeToJsonPolarisUserDetailsBuilder() throws JsonProcessingException {
		PolarisUserDetails userDetails = new PolarisUserDetails.Builder()
				.enabledTenant(true)
				.user(createMockUser(1L))
				.userAgent(() -> UserAgentParser.parse(request))
				.ipAddress(Utils.getClientIpByHeader(request))
				.loginTimeMs(Instant.now().toEpochMilli())
				.calculateExpireTime(expireDuration)
				.refreshWindowTime(refreshWindowTimeDuration.toMillis())
				.authorities(mockAuthorities())
				.build();

		String strJson = polymorphicIdObjectMapper.writeValueAsString(userDetails);
		assertThat(strJson, allOf(
				startsWith("{"),
				containsString("@class")
		));

		PolarisUserDetails deserializeBean = polymorphicIdObjectMapper.readValue(strJson, PolarisUserDetails.class);
		assertThat(deserializeBean, equalTo(userDetails));
	}

	private Set<GrantedAuthority> mockAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();

		TestGrantedAuthority adminAuthority = new TestGrantedAuthority();
		adminAuthority.setAuthority("ROLE_ADMIN");
		authorities.add(adminAuthority);

		TestGrantedAuthority tenantAuthority = new TestGrantedAuthority();
		tenantAuthority.setAuthority("ROLE_TENANT");
		authorities.add(tenantAuthority);

		return authorities;
	}

	@Override
	public PasswordEncoder passwordEncoder() {
		return passwordEncoder;
	}

	static class TestGrantedAuthority implements GrantedAuthority {

		private String authority;

		@Override
		public String getAuthority() {
			return authority;
		}

		public void setAuthority(String authority) {
			this.authority = authority;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || getClass() != o.getClass()) return false;
			TestGrantedAuthority that = (TestGrantedAuthority) o;
			return Objects.equals(authority, that.authority);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(authority);
		}
	}
}
