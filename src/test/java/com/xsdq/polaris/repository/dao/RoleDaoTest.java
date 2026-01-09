package com.xsdq.polaris.repository.dao;

import java.util.List;

import com.xsdq.polaris.repository.po.RolePO;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleDaoTest {

	@Autowired
	private RoleDao roleDao;

	@Test
	void testFindRolesByUserId() {
		List<RolePO> roles = roleDao.findRolesByUserId(1L);
		assertAll(() -> assertNotNull(roles));
	}
}
