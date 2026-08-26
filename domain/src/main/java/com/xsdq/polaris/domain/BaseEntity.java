package com.xsdq.polaris.domain;

import java.time.LocalDateTime;

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
