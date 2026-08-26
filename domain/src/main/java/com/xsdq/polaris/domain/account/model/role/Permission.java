package com.xsdq.polaris.domain.account.model.role;


import com.xsdq.polaris.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * @author XiaoYu
 * @since 2026/8/11 15:47
 */
public class Permission extends BaseEntity {

  private final PermissionId id;
  private String name;
  private final PermissionCode code;
  private String description;
  private PermissionStatus status;

  Permission(
      PermissionId id,
      String name,
      PermissionCode code,
      String description,
      PermissionStatus status,
      LocalDateTime createTime,
      LocalDateTime updateTime) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.description = description;
    this.status = status;
    super(createTime, updateTime);
  }

  public void disable() {
    if (this.status == PermissionStatus.DISABLED) throw new IllegalStateException();

    this.status = PermissionStatus.DISABLED;

    markUpdated();
  }

  public void enable() {
    if (this.status == PermissionStatus.ENABLED) throw new IllegalStateException();

    this.status = PermissionStatus.ENABLED;

    markUpdated();
  }

  public void changeName(String newName) {
    this.name = newName;
    markUpdated();
  }

  public void changeDescription(String newDescription) {
    this.description = newDescription;
    markUpdated();
  }

  public boolean isEnable() {
    return status == PermissionStatus.ENABLED;
  }

  public PermissionId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public PermissionCode getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public PermissionStatus getStatus() {
    return status;
  }

  public static Permission create(
      PermissionId id,
      String name,
      PermissionCode code,
      String description,
      LocalDateTime createTime) {
    return new Permission(
        id, name, code, description, PermissionStatus.ENABLED, createTime, createTime);
  }

  public static Permission reconstitute(
      PermissionId id,
      String name,
      PermissionCode code,
      String description,
      PermissionStatus status,
      LocalDateTime createTime,
      LocalDateTime updateTime) {
    return new Permission(id, name, code, description, status, createTime, updateTime);
  }
}
