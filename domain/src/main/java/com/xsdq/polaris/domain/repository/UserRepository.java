package com.xsdq.polaris.domain.repository;

import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.domain.model.user.Account;
import com.xsdq.polaris.domain.model.user.Email;
import com.xsdq.polaris.domain.model.user.User;
import com.xsdq.polaris.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;

/**
 * @author XiaoYu
 * @since 2026/8/11 15:48
 */
public interface UserRepository {

  Optional<User> findById(UserId id);

  Optional<User> findByAccount(Account account);

  Optional<User> findByEmail(Email email);

  List<User> findAll(TenantId tenantId);

  User update(User user);

  User save(User user);
}
