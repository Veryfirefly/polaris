package com.xsdq.polaris.account.domain.model.tenant;

import com.xsdq.polaris.account.domain.model.BaseEntity;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author XiaoYu
 * @since 2026/8/11 16:43
 */
public class Tenant extends BaseEntity {

  private final TenantId id;
  private String name;
  private String description;
  private String address;
  private String contact;
  private String logo;
  private TenantStatus status;

  Tenant(
      TenantId id,
      String name,
      String description,
      String address,
      String contact,
      String logo,
      TenantStatus status,
      LocalDateTime createTime,
      LocalDateTime updateTime) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.address = address;
    this.contact = contact;
    this.logo = logo;
    this.status = status;
    super(createTime, updateTime);
  }

  public boolean isNormal() {
    return this.status == TenantStatus.NORMAL;
  }

  public void frozen() {
    if (this.status == TenantStatus.FROZEN) throw new IllegalStateException();

    this.status = TenantStatus.FROZEN;
    markUpdated();
  }

  public void normal() {
    if (this.status == TenantStatus.NORMAL) throw new IllegalStateException();

    this.status = TenantStatus.NORMAL;
    markUpdated();
  }

  public void expired() {
    if (this.status == TenantStatus.EXPIRED) throw new IllegalStateException();

    this.status = TenantStatus.EXPIRED;
    markUpdated();
  }

  public void changeName(String name) {
    this.name = name;
    markUpdated();
  }

  public void changeDescription(String description) {
    this.description = description;
    markUpdated();
  }

  public void changeAddress(String address) {
    this.address = address;
    markUpdated();
  }

  public void changeContact(String contact) {
    this.contact = contact;
    markUpdated();
  }

  public void changeLogo(String logo) {
    this.logo = logo;
    markUpdated();
  }

  public TenantId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getAddress() {
    return address;
  }

  public String getContact() {
    return contact;
  }

  public String getLogo() {
    return logo;
  }

  public TenantStatus getStatus() {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Tenant tenant = (Tenant) o;
    return Objects.equals(id, tenant.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  public static Tenant create(
      TenantId id,
      String name,
      String description,
      String address,
      String contact,
      String logo,
      LocalDateTime createTime) {
    return new Tenant(
        id, name, description, address, contact, logo, TenantStatus.NORMAL, createTime, createTime);
  }

  public static Tenant reconstitute(
      TenantId id,
      String name,
      String description,
      String address,
      String contact,
      String logo,
      TenantStatus status,
      LocalDateTime createTime,
      LocalDateTime updateTime) {
    return new Tenant(
        id, name, description, address, contact, logo, status, createTime, updateTime);
  }
}
