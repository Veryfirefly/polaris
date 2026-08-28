package com.xsdq.polaris.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xsdq.polaris.repository.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tenants")
public class TenantPO {

  @TableId(type = IdType.ASSIGN_ID)
  private Long id;

  private String name;
  private String description;
  private Status status;
  private String logoPath;
  private String address;
  private String contactInfo;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;

  public boolean enabled() {
    return status == Status.ENABLED;
  }
}
