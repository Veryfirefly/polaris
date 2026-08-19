package com.xsdq.polaris.account.domain.repository;

import java.util.List;

import com.xsdq.polaris.account.domain.model.tenant.Tenant;
import com.xsdq.polaris.account.domain.model.tenant.TenantId;

/**
 *
 * @author XiaoYu
 * @since 2026/8/12 16:52
 */
public interface TenantRepository {

	Tenant findById(TenantId id);

	List<Tenant> findAll();

	Tenant save(Tenant tenant);

	void deleteById(TenantId id);
}
