package com.xsdq.polaris.service;

import com.xsdq.polaris.repository.po.UserPO;

/**
 *
 * @author XiaoYu
 * @since 2025/12/26 16:25
 */
public interface UserService {

	String login(String account, String password);

	UserPO getUserByAccount(String account);
}
