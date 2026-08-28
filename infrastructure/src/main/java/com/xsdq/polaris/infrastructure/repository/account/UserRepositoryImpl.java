package com.xsdq.polaris.infrastructure.repository.account;


import java.util.List;
import java.util.Optional;

import com.xsdq.polaris.domain.model.role.Role;
import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.domain.model.user.Account;
import com.xsdq.polaris.domain.model.user.Email;
import com.xsdq.polaris.domain.model.user.User;
import com.xsdq.polaris.domain.model.user.UserId;
import com.xsdq.polaris.domain.repository.RoleRepository;
import com.xsdq.polaris.domain.repository.UserRepository;
import com.xsdq.polaris.infrastructure.mapper.account.UserMapper;
import com.xsdq.polaris.infrastructure.persistence.UserPO;
import com.xsdq.polaris.infrastructure.persistence.assembler.UserAssembler;

import org.springframework.stereotype.Repository;

/**
 * @author XiaoYu
 * @since 2026/8/11 18:07
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserMapper userMapper;
  private final RoleRepository roleRepository;

  public UserRepositoryImpl(UserMapper userMapper, RoleRepository roleRepository) {
    this.userMapper = userMapper;
	this.roleRepository = roleRepository;
  }

  @Override
  public Optional<User> findById(UserId id) {
      UserPO userPO = userMapper.selectById(id.value());
      if (userPO == null)
        return Optional.empty();

      List<Role> roles = roleRepository.findAllByUserId(TenantId.of(userPO.getTenantId()), id);
      return Optional.of(UserAssembler.toDomain(userPO, roles));
  }

  @Override
  public Optional<User> findByAccount(Account account) {
    UserPO userPO = userMapper.selectById(account.value());
    if (userPO == null)
      return Optional.empty();

    List<Role> roles = roleRepository.findAllByUserId(TenantId.of(userPO.getTenantId()), UserId.of(userPO.getId()));
    return Optional.of(UserAssembler.toDomain(userPO, roles));
  }

  @Override
  public Optional<User> findByEmail(Email email) {
    UserPO userPO = userMapper.selectById(email.address());
    if (userPO == null)
      return Optional.empty();

    List<Role> roles = roleRepository.findAllByUserId(TenantId.of(userPO.getTenantId()), UserId.of(userPO.getId()));
    return Optional.of(UserAssembler.toDomain(userPO, roles));
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
