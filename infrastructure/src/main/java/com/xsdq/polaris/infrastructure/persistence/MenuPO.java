package com.xsdq.polaris.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author XiaoYu
 * @since 2025/12/23 16:55
 */
@Data
@TableName("menus")
public class MenuPO {

  @TableId
  private Long id;
  private Long parentId;
  private String name;
  private String path;
  private String component;
  private String redirect;
  private Short type;
  private Integer sort;
  private String iconPath;
  private String title;
  @Deprecated private Short status;
  private String url;
  private String method;
  private Boolean cacheable;
  private Boolean hidden;
  private Boolean hiddenHeader;
  private Boolean hiddenChildren;
  private String target;
  private String remark;
  private Long permissionId;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;

}
