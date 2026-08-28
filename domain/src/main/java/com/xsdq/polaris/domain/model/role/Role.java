package com.xsdq.polaris.domain.model.role;


import com.xsdq.polaris.domain.BaseEntity;
import com.xsdq.polaris.domain.model.menu.MenuId;
import com.xsdq.polaris.domain.model.tenant.TenantId;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author XiaoYu
 * @since 2026/8/11 15:47
 */
public class Role extends BaseEntity {

  private final RoleId roleId;
  private final TenantId tenantId;
  private String name;
  private final RoleEntity entity;
  private String description;
  private RoleStatus status;
  private final Set<PermissionId> permissionIds;
  private Set<MenuId> menus;

  Role(
      RoleId roleId,
      TenantId tenantId,
      String name,
      RoleEntity entity,
      String description,
      RoleStatus status,
      Set<PermissionId> permissionIds,
      LocalDateTime createTime,
      LocalDateTime updateTime) {
    this.roleId = roleId;
    this.tenantId = tenantId;
    this.name = name;
    this.entity = entity;
    this.description = description;
    this.status = status;
    this.permissionIds = (permissionIds == null) ? new HashSet<>() : new HashSet<>(permissionIds);
    super(createTime, updateTime);
  }

  public boolean hasPermission(PermissionId permissionId) {
    return permissionIds.contains(permissionId);
  }

  public void grant(PermissionId permissionId) {
    if (!permissionIds.add(permissionId)) throw new IllegalStateException("角色已拥有该权限");

    markUpdated();
  }

  public void revoke(PermissionId permissionId) {
    if (!permissionIds.remove(permissionId)) throw new IllegalStateException("角色未拥有该权限");

    markUpdated();
  }

  public void disable() {
    if (this.status == RoleStatus.DISABLED) throw new IllegalStateException("角色已禁止使用");

    this.status = RoleStatus.DISABLED;
    markUpdated();
  }

  public void enable() {
    if (this.status == RoleStatus.ENABLED) throw new IllegalStateException("角色已启用");

    this.status = RoleStatus.ENABLED;
    markUpdated();
  }

  public void changeName(String newName) {
    if (newName == null || newName.isBlank()) throw new IllegalArgumentException("角色名不能为空");

    this.name = newName;
    markUpdated();
  }

  public void changeDescription(String newDescription) {
    // 修改角色描述时，传入的字符串可以为空
    this.description = newDescription;
    markUpdated();
  }

  public boolean isEnable() {
    return status == RoleStatus.ENABLED;
  }

  public RoleId getRoleId() {
    return roleId;
  }

  public TenantId getTenantId() {
    return tenantId;
  }

  public String getName() {
    return name;
  }

  public RoleEntity getEntity() {
    return entity;
  }

  public String getDescription() {
    return description;
  }

  public RoleStatus getStatus() {
    return status;
  }

  public Set<PermissionId> getPermissionIds() {
    return Collections.unmodifiableSet(permissionIds);
  }

  public static Role create(
      RoleId roleId,
      TenantId tenantId,
      String name,
      RoleEntity entity,
      String description,
      Set<PermissionId> permissionIds,
      LocalDateTime createTime) {
    return new Role(
        roleId,
        tenantId,
        name,
        entity,
        description,
        RoleStatus.ENABLED,
        permissionIds,
        createTime,
        createTime);
  }

  public static Role reconstitute(
      RoleId roleId,
      TenantId tenantId,
      String name,
      RoleEntity entity,
      String description,
      RoleStatus status,
      Set<PermissionId> permissionIds,
      LocalDateTime createTime,
      LocalDateTime updateTime) {
    return new Role(
        roleId, tenantId, name, entity, description, status, permissionIds, createTime, updateTime);
  }
}
