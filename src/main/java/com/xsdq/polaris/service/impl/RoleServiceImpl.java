package com.xsdq.polaris.service.impl;

import java.util.List;

import com.xsdq.polaris.repository.dao.RoleDao;
import com.xsdq.polaris.repository.po.RolePO;
import com.xsdq.polaris.service.RoleService;

import org.springframework.stereotype.Service;

/**
 *
 * @author XiaoYu
 * @since 2025/12/29 16:59
 */
@Service
public class RoleServiceImpl implements RoleService {

	private final RoleDao roleDao;

	public RoleServiceImpl(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<RolePO> getRolesByUserId(Long userId) {
		return roleDao.findRolesByUserId(userId);
	}
}
