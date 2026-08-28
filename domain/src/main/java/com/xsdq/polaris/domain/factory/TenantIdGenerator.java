package com.xsdq.polaris.domain.factory;

import com.xsdq.polaris.domain.model.tenant.TenantId;

/**
 *
 * @author XiaoYu
 * @since 2026/8/27 10:27
 */
public interface TenantIdGenerator {

	TenantId generate();
}
