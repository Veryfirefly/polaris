package com.xsdq.polaris.infrastructure.persistence.assembler;

import com.xsdq.polaris.domain.model.tenant.Tenant;
import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.domain.model.tenant.TenantStatus;
import com.xsdq.polaris.infrastructure.persistence.TenantPO;

public class TenantAssembler {

    public static Tenant toDomain(TenantPO tenantPO) {
        return Tenant.reconstitute(
                TenantId.of(tenantPO.getId()),
                tenantPO.getName(),
                tenantPO.getDescription(),
                tenantPO.getAddress(),
                tenantPO.getContactInfo(),
                tenantPO.getLogoPath(),
                TenantStatus.of(tenantPO.getStatus()),
                tenantPO.getCreateTime(),
                tenantPO.getUpdateTime());
    }
}
