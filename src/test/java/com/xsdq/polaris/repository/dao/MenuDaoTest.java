package com.xsdq.polaris.repository.dao;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MenuDaoTest {

	@Autowired
	private MenuDao menuDao;

	@Test
	void testFindMenusByRoleId() {
		List<Long> roles = List.of(1L);
		assertNotNull(
				assertDoesNotThrow(() -> menuDao.findMenusByRoleId(roles))
		);
	}
}
