package com.xsdq.polaris.account.infrastructure.repository;

import java.util.List;

import com.xsdq.polaris.account.domain.model.tenant.TenantId;
import com.xsdq.polaris.account.domain.model.user.User;
import com.xsdq.polaris.account.domain.repository.UserRepository;
import com.xsdq.polaris.account.infrastructure.mapper.UserMapper;
import com.xsdq.polaris.account.infrastructure.persistence.UserPO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
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
	public User findById(Long id) {
		UserPO po = userMapper.selectById(id);
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
