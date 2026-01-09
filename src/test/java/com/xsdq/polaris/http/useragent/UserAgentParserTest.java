package com.xsdq.polaris.http.useragent;

import com.xsdq.polaris.servlet.MockServletTestKit;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserAgentParserTest extends MockServletTestKit {

	@Test
	void testParseUserAgent() {
		UserAgent chromeOnWindowsUserAgent = UserAgentParser.parse(USER_AGENT_SAMPLES[0]);
		assertAll(
				() -> assertNotNull(chromeOnWindowsUserAgent),
				() -> assertEquals("Chrome", chromeOnWindowsUserAgent.getBrowser().getName()),
				() -> assertEquals("143.0.0.0", chromeOnWindowsUserAgent.getVersion()),
				() -> assertEquals("Webkit", chromeOnWindowsUserAgent.getEngine().getName()),
				() -> assertEquals("537.36", chromeOnWindowsUserAgent.getEngineVersion()),
				() -> assertEquals("Windows 10 or Windows Server 2016", chromeOnWindowsUserAgent.getOs().getName()),
				() -> assertEquals("10.0", chromeOnWindowsUserAgent.getOsVersion()),
				() -> assertEquals("Windows", chromeOnWindowsUserAgent.getPlatform().getName())
		);

		UserAgent safariOnMacUserAgent = UserAgentParser.parse(USER_AGENT_SAMPLES[1]);
		assertAll(
				() -> assertNotNull(safariOnMacUserAgent),
				() -> assertEquals("Safari", safariOnMacUserAgent.getBrowser().getName()),
				() -> assertEquals("18.6", safariOnMacUserAgent.getVersion()),
				() -> assertEquals("Webkit", safariOnMacUserAgent.getEngine().getName()),
				() -> assertEquals("605.1.15", safariOnMacUserAgent.getEngineVersion()),
				() -> assertEquals("OsX", safariOnMacUserAgent.getOs().getName()),
				() -> assertEquals("10_15_7", safariOnMacUserAgent.getOsVersion()),
				() -> assertEquals("Mac", safariOnMacUserAgent.getPlatform().getName())
		);
	}
}
