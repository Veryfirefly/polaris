package com.xsdq.polaris.domain.repository;


import com.xsdq.polaris.domain.model.role.Permission;
import com.xsdq.polaris.domain.model.role.PermissionId;
import com.xsdq.polaris.domain.model.role.RoleId;

import java.util.List;
import java.util.Optional;

/**
 * @author XiaoYu
 * @since 2026/8/12 11:44
 */
public interface PermissionRepository {

  Optional<Permission> findById(PermissionId id);

  List<Permission> findAll();

  List<Permission> findAllByRoleId(RoleId roleId);

  Permission save(Permission permission);

  void deleteById(PermissionId id);
}
