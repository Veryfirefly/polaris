package com.xsdq.polaris.account.domain.model.user;

import com.xsdq.polaris.account.domain.model.BaseEntity;
import com.xsdq.polaris.account.domain.model.role.RoleId;
import com.xsdq.polaris.account.domain.model.tenant.TenantId;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author XiaoYu
 * @since 2026/8/11 15:47
 */
public class User extends BaseEntity {

  private final UserId userId;
  private final TenantId tenantId;
  private String nickname;
  private final Account account;
  private Password password;
  private Email email;
  private String contact;
  private String address;
  private String avatar;
  private UserStatus status;
  private final Set<RoleId> roleIds;

  User(
      UserId userId,
      TenantId tenantId,
      String nickname,
      Account account,
      Password password,
      Email email,
      String contact,
      String address,
      String avatar,
      UserStatus status,
      Set<RoleId> roleIds,
      LocalDateTime createTime,
      LocalDateTime updateTime) {
    this.userId = userId;
    this.tenantId = tenantId;
    this.nickname = nickname;
    this.account = account;
    this.password = password;
    this.email = email;
    this.contact = contact;
    this.address = address;
    this.avatar = avatar;
    this.status = status;
    this.roleIds = (roleIds == null) ? new HashSet<>() : new HashSet<>(roleIds);
    super(createTime, updateTime);
  }

  public boolean hasRole(RoleId roleId) {
    return roleIds.contains(roleId);
  }

  public void removeRole(RoleId roleId) {
    Objects.requireNonNull(roleId, "");

    if (!roleIds.remove(roleId)) throw new IllegalStateException("用户未拥有该角色");

    markUpdated();
  }

  public void assignRole(RoleId roleId) {
    Objects.requireNonNull(roleId, "");

    if (!roleIds.add(roleId)) throw new IllegalStateException("用户已拥有该角色");

    markUpdated();
  }

  public boolean isNormal() {
    return this.status == UserStatus.NORMAL;
  }

  public void frozen() {
    if (this.status == UserStatus.FROZEN) throw new IllegalStateException("用户状态已为冻结中");

    this.status = UserStatus.FROZEN;
    markUpdated();
  }

  public void normal() {
    if (this.status == UserStatus.NORMAL) throw new IllegalStateException("用户状态已为正常");

    this.status = UserStatus.NORMAL;
    markUpdated();
  }

  public void changePassword(Password password) {
    this.password = password;
    markUpdated();
  }

  public void changeEmail(Email email) {
    this.email = email;
    markUpdated();
  }

  public void changeNickname(String nickname) {
    this.nickname = nickname;
    markUpdated();
  }

  public void changeContact(String contact) {
    this.contact = contact;
    markUpdated();
  }

  public void changeAddress(String address) {
    this.address = address;
    markUpdated();
  }

  public void changeAvatar(String avatar) {
    this.avatar = avatar;
    markUpdated();
  }

  public UserId getUserId() {
    return userId;
  }

  public TenantId getTenantId() {
    return tenantId;
  }

  public String getNickname() {
    return nickname;
  }

  public Account getAccount() {
    return account;
  }

  public Password getPassword() {
    return password;
  }

  public Email getEmail() {
    return email;
  }

  public String getContact() {
    return contact;
  }

  public String getAddress() {
    return address;
  }

  public String getAvatar() {
    return avatar;
  }

  public UserStatus getStatus() {
    return status;
  }

  public Set<RoleId> getRoleIds() {
    return Collections.unmodifiableSet(roleIds);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(userId);
  }

  public static User create(
      UserId userId,
      TenantId tenantId,
      String nickname,
      Account account,
      Password password,
      Email email,
      Set<RoleId> assignRoleIds,
      LocalDateTime createTime) {
    return new User(
        userId,
        tenantId,
        nickname,
        account,
        password,
        email,
        null,
        null,
        null,
        UserStatus.INACTIVE,
        assignRoleIds,
        createTime,
        createTime);
  }

  public static User reconstitute(
      UserId userId,
      TenantId tenantId,
      String nickname,
      Account account,
      Password password,
      Email email,
      String concat,
      String address,
      String avatar,
      UserStatus status,
      Set<RoleId> roleIds,
      LocalDateTime createTime,
      LocalDateTime updateTime) {
    return new User(
        userId,
        tenantId,
        nickname,
        account,
        password,
        email,
        concat,
        address,
        avatar,
        status,
        roleIds,
        createTime,
        updateTime);
  }
}
