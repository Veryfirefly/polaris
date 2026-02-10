package com.xsdq.polaris.service.impl;

import java.util.List;

import com.xsdq.polaris.repository.Permission;
import com.xsdq.polaris.repository.dao.MenuDao;
import com.xsdq.polaris.repository.po.MenuPO;
import com.xsdq.polaris.service.MenuService;

import org.springframework.stereotype.Service;

/**
 *
 * @author XiaoYu
 * @since 2026/1/13 14:48
 */
@Service
public class MenuServiceImpl implements MenuService {

	private final MenuDao menuDao;

	public MenuServiceImpl(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public List<Permission> listPermissions() {
		return menuDao.selectList(null)
				.stream()
				.filter(MenuPO::isButton)
				.map(MenuPO::createPermission)
				.toList();
	}
}
