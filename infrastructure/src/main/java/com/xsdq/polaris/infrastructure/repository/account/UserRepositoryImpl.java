package com.xsdq.polaris.infrastructure.repository.account;


import com.xsdq.polaris.domain.account.model.tenant.TenantId;
import com.xsdq.polaris.domain.account.model.user.User;
import com.xsdq.polaris.domain.account.model.user.UserId;
import com.xsdq.polaris.domain.account.repository.UserRepository;
import com.xsdq.polaris.infrastructure.mapper.account.UserMapper;
import com.xsdq.polaris.infrastructure.persistence.account.UserPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XiaoYu
 * @since 2026/8/11 18:07
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserMapper userMapper;

  public UserRepositoryImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public User findById(UserId id) {
      UserPO po = userMapper.selectById(id.id());
      return null;
  }

  @Override
  public User findByAccount(String account) {
    return null;
  }

  @Override
  public List<User> findAll(TenantId tenantId) {
    return List.of();
  }

  @Override
  public User update(User user) {
    return null;
  }

  @Override
  public User save(User user) {
    return null;
  }
}
