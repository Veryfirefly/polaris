package com.xsdq.polaris.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsdq.polaris.error.PolarisRuntimeException;
import com.xsdq.polaris.http.useragent.UserAgent;
import com.xsdq.polaris.http.useragent.UserAgentParser;
import com.xsdq.polaris.repository.Response;
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

	public static String currentClientIp() {
		return getClientIpByHeader(getRequest());
	}

	public static UserAgent getCurrentUserAgent() {
		return UserAgentParser.parse(getRequest());
	}

	public static <T> void writeBizResponse(HttpServletResponse response, Response<T> bizResponse, ObjectMapper objectMapper)
			throws IOException {
		response.setContentType("application/json");
		response.setStatus(bizResponse.status());
		response.setCharacterEncoding("UTF-8");

		objectMapper.writeValue(response.getWriter(), bizResponse);
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

	public static boolean isInnerIp(String ip) {
		if ("127.0.0.1".equals(ip) || "localhost".equals(ip))
			return true;

		String[] segments = ip.split("\\.");

		if (segments.length != 4)
			return true;

		int a = Integer.parseInt(segments[0]);
		int b = Integer.parseInt(segments[1]);

		if (a == 10)
			return true;
		else if (a == 192 && b == 168)
			return true;
		else if (a == 172 && b >= 16 && b <= 31)
			return true;
		else
			return false;
	}
}
