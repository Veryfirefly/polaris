package com.xsdq.polaris.service;

import com.xsdq.polaris.repository.po.TenantPO;

/**
 *
 * @author XiaoYu
 * @since 2025/12/26 10:25
 */
public interface TenantService {

	TenantPO findTenantById(long tenantId);
}
