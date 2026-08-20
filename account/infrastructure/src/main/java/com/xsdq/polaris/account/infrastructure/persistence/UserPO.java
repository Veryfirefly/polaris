package com.xsdq.polaris.account.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("users")
public class UserPO {

  @TableId private Long id;
  private String account;
  private String password;
  private String nickname;
  private Short status;
  private String email;
  private String phone;
  private String address;
  private String avatarPath;
  private Long tenantId;
  private Long createBy;
  private LocalDateTime createTime;
  private Long updateBy;
  private LocalDateTime updateTime;
}
