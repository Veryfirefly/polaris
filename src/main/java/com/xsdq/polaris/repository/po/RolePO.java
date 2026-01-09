package com.xsdq.polaris.repository.po;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xsdq.polaris.repository.Status;

/**
 * @author XiaoYu
 * @since 2025/12/23 16:51
 */
@TableName("roles")
public class RolePO {

	@TableId
	private Long id;
	private String name;
	private String entity;
	private String description;
	private Status status;
	private Long tenantId;
	private Long createBy;
	private LocalDateTime createTime;
	private Long updateBy;
	private LocalDateTime updateTime;
	private List<PermissionPO> permissions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public List<PermissionPO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionPO> permissions) {
		this.permissions = permissions;
	}

	public boolean enabled() {
		return status == Status.ENABLED;
	}

	public boolean disabled() {
		return status == Status.DISABLED;
	}
}
