package com.xsdq.polaris.infrastructure.repository;

import com.xsdq.polaris.domain.model.role.Permission;
import com.xsdq.polaris.domain.model.role.PermissionId;
import com.xsdq.polaris.domain.model.role.Role;
import com.xsdq.polaris.domain.model.role.RoleId;
import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.domain.model.user.UserId;
import com.xsdq.polaris.domain.repository.PermissionRepository;
import com.xsdq.polaris.domain.repository.RoleRepository;
import com.xsdq.polaris.infrastructure.mapper.RoleMapper;
import com.xsdq.polaris.infrastructure.persistence.RolePO;
import com.xsdq.polaris.infrastructure.persistence.assembler.RoleAssembler;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    public RoleRepositoryImpl(RoleMapper roleMapper, PermissionRepository permissionRepository) {
        this.roleMapper = roleMapper;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Optional<Role> findById(RoleId id) {
        RolePO rolePO = roleMapper.selectById(id.value());
        if (rolePO == null)
            return Optional.empty();

        TenantId tenantId = TenantId.of(rolePO.getTenantId());
        Set<PermissionId> permissionIds = permissionRepository.findAllByRoleId(id)
                .stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());

        return Optional.of(RoleAssembler.toDomain(tenantId, rolePO, permissionIds));
    }

    @Override
    public List<Role> findAll(TenantId tenantId) {
        List<RolePO> roleCollection = roleMapper.selectByTenantId(tenantId.value());
        if (CollectionUtils.isEmpty(roleCollection))
            return List.of();

        return assembleRoles(tenantId, roleCollection);
    }

    @Override
    public List<Role> findAllByUserId(TenantId tenantId, UserId userId) {
        List<RolePO> roleCollection = roleMapper.selectByUserId(userId.value());
        if (CollectionUtils.isEmpty(roleCollection))
            return List.of();

        return assembleRoles(tenantId, roleCollection);
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public void deleteById(RoleId roleId) {

    }

    private List<Role> assembleRoles(TenantId tenantId, List<RolePO> roleCollection) {
        List<Role> roles = new ArrayList<>(roleCollection.size());
        for (RolePO rolePO : roleCollection) {
            Set<PermissionId> permissionIds = permissionRepository.findAllByRoleId(RoleId.of(rolePO.getId()))
                    .stream()
                    .map(Permission::getId)
                    .collect(Collectors.toSet());
            roles.add(RoleAssembler.toDomain(tenantId, rolePO, permissionIds));
        }
        return roles;
    }
}
