package com.xsdq.polaris.service;

import java.util.List;

import com.xsdq.polaris.repository.vo.MenuRouter;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class MenuServiceTest {

	@Autowired
	private MenuService menuService;

	@Test
	void testFindMenuRouters() {
		List<MenuRouter> routers = assertDoesNotThrow(() -> menuService.findMenuRouters(List.of(1L)));
		assertAll(
				() -> assertFalse(routers.isEmpty()),
				() -> assertFalse(routers.getFirst().children().isEmpty())
		);
	}
}
