package com.xsdq.polaris.service;

import java.util.List;

import com.xsdq.polaris.repository.po.UserPO;
import com.xsdq.polaris.repository.vo.LoginRequest;
import com.xsdq.polaris.tenant.TenantId;

/**
 *
 * @author XiaoYu
 * @since 2025/12/26 16:25
 */
public interface UserService {

	UserPO getUserByAccount(String account);

	List<UserPO> listUsers(TenantId tenantId);
}
