package com.xsdq.polaris.domain.account.repository;


import com.xsdq.polaris.domain.account.model.role.Permission;
import com.xsdq.polaris.domain.account.model.role.PermissionId;
import com.xsdq.polaris.domain.account.model.role.RoleId;
import com.xsdq.polaris.domain.account.model.tenant.TenantId;

import java.util.List;

/**
 * @author XiaoYu
 * @since 2026/8/12 11:44
 */
public interface PermissionRepository {

  Permission findById(PermissionId id);

  List<Permission> findAll(TenantId tenantId);

  List<Permission> findAllByRoleId(TenantId tenantId, RoleId roleId);

  Permission save(Permission permission);

  void deleteById(PermissionId id);
}
