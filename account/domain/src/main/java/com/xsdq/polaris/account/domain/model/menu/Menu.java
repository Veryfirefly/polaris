package com.xsdq.polaris.account.domain.model.menu;

import java.time.LocalDateTime;

import com.xsdq.polaris.account.domain.model.BaseEntity;
import com.xsdq.polaris.account.domain.model.role.PermissionId;

/**
 * @author XiaoYu
 * @since 2026/8/18 10:17
 */
public class Menu extends BaseEntity {

  public static final MenuId DEFAULT_PARENT_ID = MenuId.of(0L);

  private final MenuId id;
  private MenuId parentId;
  private MenuName name;
  private FrontendURI frontendUri;
  private String component;
  private String redirect;
  private MenuType type;
  private int sort;
  private String icon;
  private String title;
  private boolean cacheable;
  private boolean hidden;
  private boolean hiddenHeader;
  private boolean hiddenChildren;
  private String target;
  private String remark;
  private BackendURI backendUri;
  private PermissionId permissionId;
  private MenuStatus status;

  // This constructor contains all the member variables.
  Menu(
      MenuId id,
      MenuId parentId,
      MenuName name,
      FrontendURI frontendUri,
      String component,
      String redirect,
      MenuType type,
      int sort,
      String icon,
      String title,
      boolean cacheable,
      boolean hidden,
      boolean hiddenHeader,
      boolean hiddenChildren,
      String target,
      String remark,
      BackendURI backendUri,
      PermissionId permissionId,
      MenuStatus status,
      LocalDateTime createTime,
      LocalDateTime updateTime) {
    this.id = id;
    this.parentId = parentId;
    this.name = name;
    this.frontendUri = frontendUri;
    this.component = component;
    this.redirect = redirect;
    this.type = type;
    this.sort = sort;
    this.icon = icon;
    this.title = title;
    this.cacheable = cacheable;
    this.hidden = hidden;
    this.hiddenHeader = hiddenHeader;
    this.hiddenChildren = hiddenChildren;
    this.target = target;
    this.remark = remark;
    this.backendUri = backendUri;
    this.permissionId = permissionId;
    this.status = status;
    super(createTime, updateTime);
  }

  public void changeParentId(MenuId parentId) {
    this.parentId = parentId;
    markUpdated();
  }

  // todo menu name用于前端路由使用, 类似于unique key, 通常不建议更改, 如果更改则要对其做重复性验证
  public void changeMenuName(MenuName name) {
    this.name = name;
    markUpdated();
  }

  public void changeFrontendUri(FrontendURI frontendUri) {
    this.frontendUri = frontendUri;
    markUpdated();
  }

  public void changeComponent(String component) {
    this.component = component;
    markUpdated();
  }

  public void changeRedirect(String redirect) {
    this.redirect = redirect;
    markUpdated();
  }

  public void changeType(MenuType type) {
    this.type = type;
    markUpdated();
  }

  public void changeSort(int sort) {
    this.sort = sort;
    markUpdated();
  }

  public void changeIcon(String icon) {
    this.icon = icon;
    markUpdated();
  }

  public void changeTitle(String title) {
    this.title = title;
    markUpdated();
  }

  public void cacheable() {
    this.cacheable = true;
    markUpdated();
  }

  public void uncacheable() {
    this.cacheable = false;
    markUpdated();
  }

  public void hidden() {
    this.hidden = true;
    markUpdated();
  }

  public void visible() {
    this.hidden = false;
    markUpdated();
  }

  public void hiddenHeader() {
    if (type == MenuType.MENU) {
      this.hiddenHeader = true;
      markUpdated();
    }
  }

  public void visibleHeader() {
    if (type == MenuType.MENU) {
      this.hiddenHeader = false;
      markUpdated();
    }
  }

  public void hiddenChildren() {
    if (type == MenuType.MENU) {
      this.hiddenChildren = true;
      markUpdated();
    }
  }

  public void visibleChildren() {
    if (type == MenuType.MENU) {
      this.hiddenChildren = false;
      markUpdated();
    }
  }

  public void changeTarget(String target) {
    this.target = target;
    markUpdated();
  }

  public void changeRemark(String remark) {
    this.remark = remark;
    markUpdated();
  }

  public void changeBackendUri(BackendURI backendUri) {
    this.backendUri = backendUri;
    markUpdated();
  }

  public void changePermission(PermissionId permissionId) {
    this.permissionId = permissionId;
    markUpdated();
  }

  public void changeStatus(MenuStatus status) {
    this.status = status;
    markUpdated();
  }

  public void disable() {
    if (this.status == MenuStatus.DISABLED) throw new IllegalStateException("菜单状态已为禁用状态");

    this.status = MenuStatus.DISABLED;
    markUpdated();
  }

  public void enable() {
    if (status == MenuStatus.ENABLED) throw new IllegalStateException("菜单状态已为启用状态");

    this.status = MenuStatus.ENABLED;
    markUpdated();
  }

  public boolean isEnable() {
    return this.status == MenuStatus.ENABLED;
  }

  public MenuId getId() {
    return id;
  }

  public MenuId getParentId() {
    return parentId;
  }

  public MenuName getName() {
    return name;
  }

  public FrontendURI getFrontendUri() {
    return frontendUri;
  }

  public String getComponent() {
    return component;
  }

  public String getRedirect() {
    return redirect;
  }

  public MenuType getType() {
    return type;
  }

  public int getSort() {
    return sort;
  }

  public String getIcon() {
    return icon;
  }

  public String getTitle() {
    return title;
  }

  public boolean isCacheable() {
    return cacheable;
  }

  public boolean isHidden() {
    return hidden;
  }

  public boolean isHiddenHeader() {
    return hiddenHeader;
  }

  public boolean isHiddenChildren() {
    return hiddenChildren;
  }

  public String getTarget() {
    return target;
  }

  public String getRemark() {
    return remark;
  }

  public BackendURI getBackendUri() {
    return backendUri;
  }

  public PermissionId getPermissionId() {
    return permissionId;
  }

  public MenuStatus getStatus() {
    return status;
  }

  public static Menu create(
      MenuId id,
      MenuId parentId,
      MenuName name,
      FrontendURI frontendUri,
      String component,
      String redirect,
      MenuType type,
      int sort,
      String icon,
      String title,
      boolean cacheable,
      boolean hidden,
      boolean hiddenHeader,
      boolean hiddenChildren,
      String target,
      String remark,
      BackendURI backendUri,
      PermissionId permissionId,
      LocalDateTime createTime) {
    return switch (type) {
      case DIRECTORY -> createDirectory(id, name, frontendUri, redirect, sort, icon, title, hidden, remark, createTime);
      case MENU -> createMenu(id, parentId, name, frontendUri, component, sort, icon, title, cacheable, hidden, hiddenHeader, hiddenChildren, target, remark, createTime);
      case API -> createApi();
    };
  }

  public static Menu reconstitute() {
    return null;
  }

  // 通常情况下, 目录不需要parentId
  private static Menu createDirectory(
          MenuId id,
          MenuName name,
          FrontendURI frontendUri,
          String redirect,
          int sort,
          String icon,
          String title,
          boolean hidden,
          String remark,
          LocalDateTime createTime) {
    if (redirect == null || redirect.isBlank())
      throw new IllegalArgumentException("目录菜单的跳转地址配置不能为空");
    if (title == null || title.isBlank())
      throw new IllegalArgumentException("目录菜单的名称配置不能为空");

    return new Menu(
            id,
            DEFAULT_PARENT_ID,
            name,
            frontendUri,
            null,
            redirect,
            MenuType.DIRECTORY,
			Math.max(sort, 0),
            icon,
            title,
            false,
            hidden,
            false,
            false,
            null,
            remark,
            null,
            null,
            MenuStatus.ENABLED,
            createTime,
            createTime);
  }

  private static Menu createMenu(
          MenuId id,
          MenuId parentId,
          MenuName name,
          FrontendURI frontendUri,
          String component,
          int sort,
          String icon,
          String title,
          boolean cacheable,
          boolean hidden,
          boolean hiddenHeader,
          boolean hiddenChildren,
          String target,
          String remark,
          LocalDateTime createTime) {
    return null;
  }

  private static Menu createApi() {
    return null;
  }
}
