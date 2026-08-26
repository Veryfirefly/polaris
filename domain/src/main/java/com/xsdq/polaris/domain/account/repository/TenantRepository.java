package com.xsdq.polaris.domain.account.repository;

import com.xsdq.polaris.domain.account.model.tenant.Tenant;
import com.xsdq.polaris.domain.account.model.tenant.TenantId;

import java.util.List;

/**
 * @author XiaoYu
 * @since 2026/8/12 16:52
 */
public interface TenantRepository {

  Tenant findById(TenantId id);

  List<Tenant> findAll();

  Tenant save(Tenant tenant);

  void deleteById(TenantId id);
}
