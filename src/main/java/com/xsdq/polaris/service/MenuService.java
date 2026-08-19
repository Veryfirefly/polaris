package com.xsdq.polaris.service;

import java.util.List;

import com.xsdq.polaris.repository.Permission;
import com.xsdq.polaris.repository.vo.MenuRouter;

/**
 *
 * @author XiaoYu
 * @since 2026/1/13 12:38
 */
public interface MenuService {

	List<Permission> listPermissions();

	List<MenuRouter> findMenuRouters(List<Long> list);
}
