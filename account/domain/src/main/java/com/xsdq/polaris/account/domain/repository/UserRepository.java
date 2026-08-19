package com.xsdq.polaris.account.domain.repository;

import java.util.List;

import com.xsdq.polaris.account.domain.model.tenant.TenantId;
import com.xsdq.polaris.account.domain.model.user.User;
import com.xsdq.polaris.account.domain.model.user.UserId;

/**
 *
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
