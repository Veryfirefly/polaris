package com.xsdq.polaris.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xsdq.polaris.account.infrastructure.persistence.MenuPO;
import com.xsdq.polaris.repository.Permission;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author XiaoYu
 * @since 2025/12/23 16:51
 */
@Data
@TableName("roles")
public class RolePO {

  @TableId private Long id;
  private String name;
  private String entity;
  private String description;
  private Short status;
  private Long tenantId;
  private Long createBy;
  private LocalDateTime createTime;
  private Long updateBy;
  private LocalDateTime updateTime;
  private List<MenuPO> permissions;

  public List<Permission> permissions() {
    return permissions.stream().filter(MenuPO::isButton).map(MenuPO::createPermission).toList();
  }

  @Deprecated
  public GrantedAuthority authority() {
    return new SimpleGrantedAuthority("ROLE_" + entity);
  }

  public boolean enable() {
    return status == Status.ENABLED;
  }
}
