package com.xsdq.polaris.account.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;

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
	private String url;
	private String method;
	private String path;
	private String component;
	private String redirect;
	private Type type;
	@TableField("`order`")
	private Integer order;
	private Status status;
	private String permission;
	private String iconPath;
	private String title;
	private Boolean cacheable;
	private Boolean hidden;
	private Boolean hiddenHeader;
	private Boolean hiddenChildren;
	private String target;
	private String remark;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

	public Permission createPermission() {
		if (type != Type.BUTTON)
			throw new IllegalStateException("This menu does not grant permissions.");
		return new Permission(id, name, url, method, status, permission);
	}

	public boolean isButton() {
		return type == Type.BUTTON;
	}

	public boolean isDirOrMenu() {
		return type == Type.DIRECTORY || type == Type.MENU;
	}

	public boolean isTopLevel() {
		return parentId == 0;
	}

	public boolean enabled() {
		return status == Status.ENABLED;
	}

	@Getter
	public enum Type {
		DIRECTORY((short) 0),
		MENU((short) 1),
		BUTTON((short) 2);

		@EnumValue
		private final Short val;

		Type(Short val) {
			this.val = val;
		}
	}
}
