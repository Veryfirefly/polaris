package com.xsdq.polaris.service.impl;

import com.xsdq.polaris.repository.dao.UserDao;
import com.xsdq.polaris.repository.po.UserPO;
import com.xsdq.polaris.service.UserService;
import com.xsdq.polaris.tenant.TenantId;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author XiaoYu
 * @since 2025/12/26 16:27
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserPO getUserByAccount(String account) {
    return userDao.findByAccount(account);
  }

  @Override
  public List<UserPO> listUsers(TenantId tenantId) {
    return userDao.findUsersByTenantId(tenantId.id());
  }
}
