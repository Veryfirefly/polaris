package com.xsdq.polaris.domain.repository;

import com.xsdq.polaris.domain.model.role.Role;
import com.xsdq.polaris.domain.model.role.RoleId;
import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;

/**
 * @author XiaoYu
 * @since 2026/8/12 11:37
 */
public interface RoleRepository {

  /**
   * 通过指定的角色id查询相应的角色
   *
   * @param id RoleId
   * @return corresponding role
   */
  Optional<Role> findById(RoleId id);

  /**
   * 由于角色采用租户隔离，不同租户下的角色对于其它租户则是透明的。
   * 所以需要通过租户id查询当前租户下关联的角色集合。
   *
   * @param tenantId 租户id
   * @return corresponding roles
   */
  List<Role> findAll(TenantId tenantId);

  /**
   * 通过指定的用户id，查询当前租户下的该用户拥有哪些角色集合。
   *
   * @param tenantId
   * @param userId
   * @return
   */
  List<Role> findAllByUserId(TenantId tenantId, UserId userId);

  Role save(Role role);

  void deleteById(RoleId roleId);
}
