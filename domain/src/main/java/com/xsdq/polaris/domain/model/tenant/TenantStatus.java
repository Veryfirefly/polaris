package com.xsdq.polaris.domain.model.tenant;

/**
 * 描述租户状态:
 *
 * <ul>
 *   <li>正常(normal)
 *   <li>过期(frozen)
 *   <li>冻结(expired)
 * </ul>
 *
 * @author XiaoYu
 * @since 2026/8/11 16:45
 */
public enum TenantStatus {
  FROZEN((short) 0),
  NORMAL((short) 1),
  EXPIRED((short) 2);

  private final short status;

  TenantStatus(short status) {
    this.status = status;
  }

  public short getStatus() {
    return status;
  }
}
