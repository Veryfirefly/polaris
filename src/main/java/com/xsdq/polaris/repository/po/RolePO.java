package com.xsdq.polaris.repository.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xsdq.polaris.repository.Permission;
import com.xsdq.polaris.repository.Status;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
  private Status status;
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
