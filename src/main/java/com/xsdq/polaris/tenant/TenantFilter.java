package com.xsdq.polaris.tenant;

import java.io.IOException;
import java.util.Enumeration;

import jakarta.annotation.Nonnull;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author XiaoYu
 * @since 2026/6/1 16:01
 */
public class TenantFilter implements Filter {

	private static final String TENANT_HEADER = "X-Tenant-Id";

	private final Logger log = LoggerFactory.getLogger(TenantFilter.class);

	void iteratePrintHeader(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = request.getHeader(headerName);

			log.info("Http header {}: {}", headerName, headerValue);
		}
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest,
			ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		try {
			iteratePrintHeader(request);

			String tenantId = request.getHeader(TENANT_HEADER);
			if (StringUtils.hasText(tenantId)) {
				TenantContext.setTenantId(new TenantId(Long.parseLong(tenantId)));
				log.info("TenantId has been set to {}", tenantId);
			}

			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			TenantContext.clear();
		}
	}
}
