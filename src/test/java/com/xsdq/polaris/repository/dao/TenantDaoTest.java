package com.xsdq.polaris.repository.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsdq.polaris.repository.Status;
import com.xsdq.polaris.repository.po.TenantPO;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TenantDaoTest {

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateNewTenant() {
        var tenant = createMockTenant();
        int numResult = tenantDao.insert(tenant);
        assertTrue(numResult > 0);
    }

    @Test
    void testQueryTenant() {
        List<TenantPO> tenants = tenantDao.selectList(null);
        assertNotNull(tenants);
    }

    @Test
    void testUpdateTenant() {
        var tenants = tenantDao.selectList(null);
        assertNotNull(tenants);

        var tenant = tenants.getFirst();
        tenant.setStatus(Status.DISABLED);

        int numResult = tenantDao.updateById(tenant);
        assertTrue(numResult > 0);
    }

    @Test
    void testSerializableTenantId() throws JsonProcessingException {
        var tenants = tenantDao.selectList(null);
        assertAll(
                () -> assertNotNull(tenants),
                () -> assertFalse(tenants.isEmpty())
        );

        var tenant = tenants.getFirst();
        var tenantId = new TenantId(tenant.getId());
        var strTenantIdJson = objectMapper.writeValueAsString(tenantId);
        assertAll(
                () -> assertNotNull(strTenantIdJson),
                () -> assertTrue(strTenantIdJson.startsWith("{"))
        );

        assertDoesNotThrow(() -> objectMapper.readValue(strTenantIdJson, TenantId.class));
    }

    record TenantId(long tenantId) implements Serializable {}

    TenantPO createMockTenant() {
        var tenant = new TenantPO();
        tenant.setName("Polaris mock tenant");
        tenant.setDescription("This tenant is a test case.");
        tenant.setStatus(Status.ENABLED);
        tenant.setLogoPath("https://img-polaris.xsdq.xyz/pircture/tenant/2025122415250323.jpg");
        tenant.setAddress("Fake address");
        tenant.setContactInfo("13888888888");
        return tenant;
    }
}
