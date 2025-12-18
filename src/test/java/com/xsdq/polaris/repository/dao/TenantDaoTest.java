package com.xsdq.polaris.repository.dao;

import com.xsdq.polaris.repository.Status;
import com.xsdq.polaris.repository.po.TenantPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TenantDaoTest {


    @Autowired
    private TenantDao tenantDao;

    @Test
    void testInsertDatabase() {
        TenantPO tenant = new TenantPO();
        tenant.setName("第一个租户(测试用) " + LocalTime.now());
        tenant.setIdentity(UUID.randomUUID().toString());
        tenant.setStatus(Status.ENABLED);
        tenant.setCreateTime(LocalDateTime.now());

        int numResult = tenantDao.insert(tenant);
        assertTrue(numResult > 0);
    }

    @Test
    void testQueryDatabase() {
        List<TenantPO> tenants = tenantDao.selectList(null);
        assertNotNull(tenants);
    }
}