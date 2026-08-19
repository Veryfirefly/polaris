package com.xsdq.polaris.repository;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum Status {
    DISABLED((short) 0),
    ENABLED((short) 1);

    @EnumValue
    private final short val;

    Status(short val) {
        this.val = val;
    }
}
