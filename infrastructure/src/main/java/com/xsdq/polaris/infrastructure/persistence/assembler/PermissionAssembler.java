package com.xsdq.polaris.infrastructure.persistence.assembler;

import com.xsdq.polaris.domain.model.role.Permission;
import com.xsdq.polaris.domain.model.role.PermissionCode;
import com.xsdq.polaris.domain.model.role.PermissionId;
import com.xsdq.polaris.domain.model.role.PermissionStatus;
import com.xsdq.polaris.infrastructure.persistence.PermissionPO;

public class PermissionAssembler {

    public static Permission toDomain(PermissionPO permissionPO) {
        return Permission.reconstitute(
                PermissionId.of(permissionPO.getId()),
                permissionPO.getName(),
                PermissionCode.of(permissionPO.getCode()),
                permissionPO.getDescription(),
                PermissionStatus.of(permissionPO.getStatus()),
                permissionPO.getCreateTime(),
                permissionPO.getUpdateTime());
    }
}
