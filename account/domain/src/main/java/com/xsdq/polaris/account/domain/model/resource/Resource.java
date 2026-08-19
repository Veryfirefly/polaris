package com.xsdq.polaris.account.domain.model.resource;

import java.time.LocalDateTime;

import com.xsdq.polaris.account.domain.model.BaseEntity;
import com.xsdq.polaris.account.domain.model.role.PermissionId;
import com.xsdq.polaris.account.domain.model.tenant.TenantId;

/**
 *
 * @author XiaoYu
 * @since 2026/8/18 10:17
 */
public class Resource extends BaseEntity {

	public static final ResourceId DEFAULT_PARENT_ID = ResourceId.of(0L);

	private final ResourceId id;
	private ResourceId parentId;
	private ResourceName name;
	private String path;
	private String component;
	private String redirect;
	private ResourceType type;
	private int sort;
	private String icon;
	private String title;
	private boolean cacheable;
	private boolean hidden;
	private boolean hiddenHeader;
	private boolean hiddenChildren;
	private String target;
	private String remark;
	private ApiRequestURI uri;
	private PermissionId permissionId;
	private TenantId tenantId;
	private ResourceStatus status;

	// This constructor contains all the member variables.
	Resource(ResourceId id, ResourceId parentId, ResourceName name, String path, String component,
			String redirect, ResourceType type, int sort, String icon, String title,
			boolean cacheable, boolean hidden, boolean hiddenHeader, boolean hiddenChildren,
			String target, String remark, ApiRequestURI uri, PermissionId permissionId,
			TenantId tenantId, ResourceStatus status, LocalDateTime createTime, LocalDateTime updateTime) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.path = path;
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
		this.uri = uri;
		this.permissionId = permissionId;
		this.tenantId = tenantId;
		this.status = status;
		super(createTime, updateTime);
	}

	public void changeParentId(ResourceId parentId) {
		this.parentId = parentId;
		markUpdated();
	}

	public void changeResourceName(ResourceName name) {
		this.name = name;
		markUpdated();
	}

	public void changePath(String path) {
		this.path = path;
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

	public void changeType(ResourceType type) {
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

	public void changeCacheable(boolean cacheable) {
		this.cacheable = cacheable;
		markUpdated();
	}

	public void changeHidden(boolean hidden) {
		this.hidden = hidden;
		markUpdated();
	}

	public void changeHiddenHeader(boolean hiddenHeader) {
		this.hiddenHeader = hiddenHeader;
		markUpdated();
	}

	public void changeHiddenChildren(boolean hiddenChildren) {
		this.hiddenChildren = hiddenChildren;
		markUpdated();
	}

	public void changeTarget(String target) {
		this.target = target;
		markUpdated();
	}

	public void changeRemark(String remark) {
		this.remark = remark;
		markUpdated();
	}

	public void changeApiRequestUri(ApiRequestURI uri) {
		this.uri = uri;
		markUpdated();
	}

	public void changePermission(PermissionId permissionId) {
		this.permissionId = permissionId;
		markUpdated();
	}

	public void changeStatus(ResourceStatus status) {
		this.status = status;
		markUpdated();
	}

	public void disable() {
		if (this.status == ResourceStatus.DISABLED)
			throw new IllegalStateException("菜单状态已为禁用状态");

		this.status = ResourceStatus.DISABLED;
		markUpdated();
	}

	public void enable() {
		if (status == ResourceStatus.ENABLED)
			throw new IllegalStateException("菜单状态已为启用状态");

		this.status = ResourceStatus.ENABLED;
		markUpdated();
	}

	public void assignToTenant(TenantId tenantId) {
		this.tenantId = tenantId;
		markUpdated();
	}

	public boolean isEnable() {
		return this.status == ResourceStatus.ENABLED;
	}

	public ResourceId getId() {
		return id;
	}

	public ResourceId getParentId() {
		return parentId;
	}

	public ResourceName getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getComponent() {
		return component;
	}

	public String getRedirect() {
		return redirect;
	}

	public ResourceType getType() {
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

	public ApiRequestURI getUri() {
		return uri;
	}

	public PermissionId getPermissionId() {
		return permissionId;
	}

	public TenantId getTenantId() {
		return tenantId;
	}

	public ResourceStatus getStatus() {
		return status;
	}

	public static Resource createDir(ResourceId id, ResourceId parentId, ResourceName name, String path,
			String component, String redirect) {
		return null;
	}

	public static Resource createMenu() {
		return null;
	}

	public static Resource createApi() {
		return null;
	}

	public static Resource reconstitute() {
		return null;
	}
}
