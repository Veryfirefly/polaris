package com.xsdq.polaris.service;

import java.util.List;

import com.xsdq.polaris.repository.po.RolePO;

/**
 *
 * @author XiaoYu
 * @since 2025/12/29 16:55
 */
public interface RoleService {

	List<RolePO> getRolesByUserId(Long userId);
}
