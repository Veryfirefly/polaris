package com.xsdq.polaris.util;

import com.xsdq.polaris.error.PolarisRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class Utils {

	public static final String[] PROXY_HEADERS = { "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };

	private Utils() {
		throw new IllegalStateException("Utility classes are not allowed to be initialized.");
	}

	public static HttpServletRequest getRequest() {
		return getServletRequestAttributes().getRequest();
	}

	public static HttpServletResponse getResponse() {
		return getServletRequestAttributes().getResponse();
	}

	public static ServletRequestAttributes getServletRequestAttributes() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			throw new PolarisRuntimeException("Unable to retrieve 'ServletRequestAttributes', may not be a servlet web environment.");
		}
		return attributes;
	}

	public static String getClientIpByHeader(HttpServletRequest request) {
		for (String headerName : PROXY_HEADERS) {
			String ip = request.getHeader(headerName);
			if (!isUnknown(ip)) {
				return multistageReverseProxyIp(ip);
			}
		}
		return multistageReverseProxyIp(request.getRemoteAddr());
	}

	private static boolean isUnknown(String str) {
		return !StringUtils.hasText(str) || "unknown".equalsIgnoreCase(str);
	}

	private static String multistageReverseProxyIp(String ip) {
		if (StringUtils.hasText(ip) && ip.contains(",")) {
			for (String subIp : ip.split(",")) {
				if (!isUnknown(subIp)) {
					return subIp;
				}
			}
		}
		return ip;
	}
}
