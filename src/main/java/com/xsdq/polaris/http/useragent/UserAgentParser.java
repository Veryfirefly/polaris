package com.xsdq.polaris.http.useragent;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 *
 * @author XiaoYu
 * @since 2025/12/29 17:33
 */
public class UserAgentParser {

	private static final String USER_AGENT_HEADER = "User-Agent";

	public static UserAgent parse(HttpServletRequest request) {
		return parse(request.getHeader(USER_AGENT_HEADER));
	}

	/**
	 * 解析User-Agent
	 *
	 * @param userAgentString User-Agent字符串
	 * @return {@link UserAgent}
	 */
	public static UserAgent parse(String userAgentString) {
		if(!StringUtils.hasText(userAgentString)){
			return null;
		}
		final UserAgent userAgent = new UserAgent();

		// 浏览器
		final Browser browser = parseBrowser(userAgentString);
		userAgent.setBrowser(browser);
		userAgent.setVersion(browser.getVersion(userAgentString));

		// 浏览器引擎
		final Engine engine = parseEngine(userAgentString);
		userAgent.setEngine(engine);
		userAgent.setEngineVersion(engine.getVersion(userAgentString));

		// 操作系统
		final Os os = parseOS(userAgentString);
		userAgent.setOs(os);
		userAgent.setOsVersion(os.getVersion(userAgentString));

		// 平台
		final Platform platform = parsePlatform(userAgentString);
		userAgent.setPlatform(platform);

		// issue#IA74K2 MACOS下的微信不属于移动平台
		if(platform.isMobile() || browser.isMobile()){
			if(!os.isMacOs()){
				userAgent.setMobile(true);
			}
		}
		return userAgent;
	}

	/**
	 * 解析浏览器类型
	 *
	 * @param userAgentString User-Agent字符串
	 * @return 浏览器类型
	 */
	private static Browser parseBrowser(String userAgentString) {
		for (Browser browser : Browser.browers) {
			if (browser.isMatch(userAgentString)) {
				return browser;
			}
		}
		return Browser.Unknown;
	}

	/**
	 * 解析引擎类型
	 *
	 * @param userAgentString User-Agent字符串
	 * @return 引擎类型
	 */
	private static Engine parseEngine(String userAgentString) {
		for (Engine engine : Engine.engines) {
			if (engine.isMatch(userAgentString)) {
				return engine;
			}
		}
		return Engine.Unknown;
	}

	/**
	 * 解析系统类型
	 *
	 * @param userAgentString User-Agent字符串
	 * @return 系统类型
	 */
	private static Os parseOS(String userAgentString) {
		for (Os os : Os.oses) {
			if (os.isMatch(userAgentString)) {
				return os;
			}
		}
		return Os.Unknown;
	}

	/**
	 * 解析平台类型
	 *
	 * @param userAgentString User-Agent字符串
	 * @return 平台类型
	 */
	private static Platform parsePlatform(String userAgentString) {
		for (Platform platform : Platform.platforms) {
			if (platform.isMatch(userAgentString)) {
				return platform;
			}
		}
		return Platform.Unknown;
	}
}
