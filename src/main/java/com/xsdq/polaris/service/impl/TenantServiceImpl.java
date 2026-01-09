package com.xsdq.polaris.service.impl;

import com.xsdq.polaris.repository.dao.TenantDao;
import com.xsdq.polaris.repository.po.TenantPO;
import com.xsdq.polaris.service.TenantService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author XiaoYu
 * @since 2025/12/26 11:18
 */
@Service
public class TenantServiceImpl implements TenantService {

	private final TenantDao tenantDao;

	public TenantServiceImpl(TenantDao tenantDao) {
		this.tenantDao = tenantDao;
	}

	@Override
	@Cacheable(cacheNames = "polaris:tenant", key = "#tenantId")
	public TenantPO getTenantById(long tenantId) {
		return tenantDao.selectById(tenantId);
	}
}
