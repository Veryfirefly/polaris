package com.xsdq.polaris.repository.dao;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserDaoTests implements UserMockDataTestKit {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TenantDao tenantDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;

	@Test
	void testCreateNewUser() {
		var tenants = tenantDao.selectList(null);
		assertNotNull(tenants);

		var tenant = tenants.getFirst();
		var user = createMockUser(tenant.getId());

		int numResult = userDao.insert(user);
		assertTrue(numResult > 0);
	}

	@Test
	void testQueryUser() {
		var user = userDao.findByAccount("xiaoyu");
		assertNotNull(user);
	}

	@Test
	void testFindUserByAccount() {
		var user = userDao.findByAccount("xiaoyu");
		assertAll(
				() -> assertNotNull(user)
		);
	}

	@Override
	public PasswordEncoder passwordEncoder() {
		return passwordEncoder;
	}
}
