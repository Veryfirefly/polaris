package com.xsdq.polaris.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.xsdq.polaris.repository.vo.MenuRouter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MenuServiceTest {

  @Autowired private MenuService menuService;

  @Test
  void testFindMenuRouters() {
    List<MenuRouter> routers = assertDoesNotThrow(() -> menuService.findMenuRouters(List.of(1L)));
    assertAll(
        () -> assertFalse(routers.isEmpty()),
        () -> assertFalse(routers.getFirst().children().isEmpty()));
  }
}
