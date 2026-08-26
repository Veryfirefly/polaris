package com.xsdq.polaris.domain.account.repository;

import com.xsdq.polaris.domain.account.model.tenant.TenantId;
import com.xsdq.polaris.domain.account.model.user.User;
import com.xsdq.polaris.domain.account.model.user.UserId;

import java.util.List;

/**
 * @author XiaoYu
 * @since 2026/8/11 15:48
 */
public interface UserRepository {

  User findById(UserId id);

  User findByAccount(String account);

  List<User> findAll(TenantId tenantId);

  User update(User user);

  User save(User user);
}
