package com.xsdq.polaris.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RoleServiceImpl extends ServiceImpl<RoleDao, RolePO> implements RoleService {

	@Override
	public List<RolePO> getRolesByUserId(Long userId) {
		return baseMapper.findRolesByUserId(userId);
	}
}
