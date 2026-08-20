package com.xsdq.polaris.account.domain.repository;

import com.xsdq.polaris.account.domain.model.role.Role;
import com.xsdq.polaris.account.domain.model.role.RoleId;
import com.xsdq.polaris.account.domain.model.tenant.TenantId;
import com.xsdq.polaris.account.domain.model.user.UserId;
import java.util.List;

/**
 * @author XiaoYu
 * @since 2026/8/12 11:37
 */
public interface RoleRepository {

  Role findById(RoleId id);

  List<Role> findAll(TenantId tenantId);

  List<Role> findAllByUserId(TenantId tenantId, UserId userId);

  Role save(Role role);

  void deleteById(RoleId roleId);
}
