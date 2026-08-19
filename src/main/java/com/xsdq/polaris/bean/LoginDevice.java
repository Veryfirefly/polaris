package com.xsdq.polaris.bean;

import com.xsdq.polaris.cache.Cacheable;
import com.xsdq.polaris.http.useragent.UserAgent;

import static com.xsdq.polaris.constant.PolarisConstant.EMPTY_STR;

public record LoginDevice(String os,
						  String osVersion,
						  String engine,
						  String engineVersion,
						  String browser,
						  String browserVersion,
						  String platform,
						  boolean isMobile) implements Cacheable {

	public static final LoginDevice EMPTY = new LoginDevice(EMPTY_STR, EMPTY_STR, EMPTY_STR, EMPTY_STR, EMPTY_STR,
			EMPTY_STR, EMPTY_STR, false);

	public static LoginDevice create(UserAgent userAgent) {
		if (userAgent == null) {
			return EMPTY;
		}

		return new LoginDevice(
				userAgent.getOs().getName(),
				userAgent.getOsVersion(),
				userAgent.getEngine().getName(),
				userAgent.getEngineVersion(),
				userAgent.getBrowser().getName(),
				userAgent.getVersion(),
				userAgent.getPlatform().getName(),
				userAgent.isMobile()
		);
	}
}
