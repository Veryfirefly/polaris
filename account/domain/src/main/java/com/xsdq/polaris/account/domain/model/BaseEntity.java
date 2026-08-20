package com.xsdq.polaris.account.domain.model;

import java.time.LocalDateTime;

/**
 * @author XiaoYu
 * @since 2026/8/19 13:40
 */
public abstract class BaseEntity {

  private final LocalDateTime createTime;
  private LocalDateTime updateTime;

  public BaseEntity(LocalDateTime createTime, LocalDateTime updateTime) {
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  protected void markUpdated() {
    this.updateTime = LocalDateTime.now();
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }
}
