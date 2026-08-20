package com.xsdq.polaris.account.domain.repository;

import com.xsdq.polaris.account.domain.model.role.Permission;
import com.xsdq.polaris.account.domain.model.role.PermissionId;
import com.xsdq.polaris.account.domain.model.role.RoleId;
import com.xsdq.polaris.account.domain.model.tenant.TenantId;
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
