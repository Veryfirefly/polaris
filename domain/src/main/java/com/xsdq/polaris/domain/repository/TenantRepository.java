package com.xsdq.polaris.domain.repository;

import com.xsdq.polaris.domain.model.tenant.Tenant;
import com.xsdq.polaris.domain.model.tenant.TenantId;

import java.util.List;
import java.util.Optional;

/**
 * @author XiaoYu
 * @since 2026/8/12 16:52
 */
public interface TenantRepository {

  Optional<Tenant> findById(TenantId id);

  List<Tenant> findAll();

  Tenant save(Tenant tenant);

  void deleteById(TenantId id);
}
