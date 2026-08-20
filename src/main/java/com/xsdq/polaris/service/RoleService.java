package com.xsdq.polaris.service;

import com.xsdq.polaris.repository.po.RolePO;
import java.util.List;

/**
 * @author XiaoYu
 * @since 2025/12/29 16:55
 */
public interface RoleService {

  List<RolePO> getRolesByUserId(Long userId);
}
