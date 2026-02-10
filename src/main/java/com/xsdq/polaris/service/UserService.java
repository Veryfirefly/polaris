package com.xsdq.polaris.service;

import com.xsdq.polaris.repository.po.UserPO;
import com.xsdq.polaris.repository.vo.LoginRequest;

/**
 *
 * @author XiaoYu
 * @since 2025/12/26 16:25
 */
public interface UserService {

	// todo maybe we don't need the method
	@Deprecated
	String login(LoginRequest request);

	UserPO getUserByAccount(String account);
}
