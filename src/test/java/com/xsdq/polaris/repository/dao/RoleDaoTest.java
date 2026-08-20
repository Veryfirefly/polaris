package com.xsdq.polaris.repository.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.xsdq.polaris.repository.po.RolePO;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RoleDaoTest {

  @Autowired private RoleDao roleDao;

  @Test
  void testFindRolesByUserId() {
    List<RolePO> roles = roleDao.findRolesByUserId(1L);
    assertAll(() -> assertNotNull(roles));
  }
}
