package com.xsdq.polaris.bean.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xsdq.polaris.bean.LoginStatus;
import lombok.Data;

/**
 * 用户登录记录
 *
 * @author XiaoYu
 * @since 2026/6/26 14:53
 */
@Data
@TableName("user_login_history")
public class LoginHistoryPO {

	@TableId
	private Long id;
	private Long userId;
	private String account;
	private Long tenantId;
	private String os;
	@TableField("os_version")
	private String osVersion;
	private String platform;
	private String browser;
	@TableField("browser_version")
	private String browserVersion;
	private String engine;
	@TableField("engine_version")
	private String engineVersion;
	@TableField("ip_addr")
	private String ipAddr;
	private String country;
	private String province;
	private String city;
	private String isp;
	@TableField("is_mobile")
	private Boolean mobile; // false: desktop, true: mobile
	@TableField("login_status")
	private LoginStatus loginStatus;
	private LocalDateTime createTime;

}
