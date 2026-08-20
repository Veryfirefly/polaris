package com.xsdq.polaris.service;

import com.xsdq.polaris.repository.Permission;
import com.xsdq.polaris.repository.vo.MenuRouter;
import java.util.List;

/**
 * @author XiaoYu
 * @since 2026/1/13 12:38
 */
public interface MenuService {

  List<Permission> listPermissions();

  List<MenuRouter> findMenuRouters(List<Long> list);
}
