package com.xsdq.polaris.repository.dao;

import com.xsdq.polaris.repository.Status;
import com.xsdq.polaris.repository.po.UserPO;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserDaoTest {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TenantDao tenantDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
				() -> assertNotNull(user),
				() -> assertNotNull(user.getTenant()),
				() -> assertNotNull(user.getRoles()),
				() -> assertFalse(user.getRoles().isEmpty())
		);
	}

	UserPO createMockUser(long tenantId) {
		var user = new UserPO();
		user.setAccount("xiaoyu");
		user.setPassword(passwordEncoder.encode("123456"));
		user.setTenantId(tenantId);
		user.setNickname("测试昵称");
		user.setStatus(Status.ENABLED);
		user.setEmail("xiaoyu@polaris.com");
		user.setPhone("13112345678");
		user.setCreateBy(0L);
		user.setUpdateBy(0L);
		return user;
	}
}
