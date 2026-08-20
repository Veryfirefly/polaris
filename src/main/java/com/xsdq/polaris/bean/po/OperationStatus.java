package com.xsdq.polaris.bean.po;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OperationStatus {
  ABNORMAL((short) 0),
  NORMAL((short) 1);

  @EnumValue private final short value;

  OperationStatus(short value) {
    this.value = value;
  }
}
