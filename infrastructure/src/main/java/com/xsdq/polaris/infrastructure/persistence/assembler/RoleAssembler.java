package com.xsdq.polaris.infrastructure.persistence.assembler;

import com.xsdq.polaris.domain.model.role.*;
import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.infrastructure.persistence.RolePO;

import java.util.Set;

public class RoleAssembler {

    public static Role toDomain(TenantId tenantId, RolePO rolePO, Set<PermissionId> permissionIds) {
        return Role.reconstitute(
                RoleId.of(rolePO.getId()),
                tenantId,
                rolePO.getName(),
                RoleEntity.of(rolePO.getEntity()),
                rolePO.getDescription(),
                RoleStatus.of(rolePO.getStatus()),
                permissionIds,
                rolePO.getCreateTime(),
                rolePO.getUpdateTime());
    }
}
