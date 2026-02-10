package com.xsdq.polaris.service;

import java.util.List;

import com.xsdq.polaris.repository.Permission;

/**
 *
 * @author XiaoYu
 * @since 2026/1/13 12:38
 */
public interface MenuService {

	List<Permission> listPermissions();
}
