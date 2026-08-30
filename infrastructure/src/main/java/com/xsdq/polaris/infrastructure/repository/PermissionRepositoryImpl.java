package com.xsdq.polaris.infrastructure.repository;

import com.xsdq.polaris.domain.model.role.Permission;
import com.xsdq.polaris.domain.model.role.PermissionId;
import com.xsdq.polaris.domain.model.role.RoleId;
import com.xsdq.polaris.domain.repository.PermissionRepository;
import com.xsdq.polaris.infrastructure.mapper.PermissionMapper;
import com.xsdq.polaris.infrastructure.persistence.PermissionPO;
import com.xsdq.polaris.infrastructure.persistence.assembler.PermissionAssembler;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionMapper permissionMapper;

    public PermissionRepositoryImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Optional<Permission> findById(PermissionId id) {
        PermissionPO permissionPO = permissionMapper.selectById(id.value());
        if (permissionPO == null)
            return Optional.empty();
        return Optional.of(PermissionAssembler.toDomain(permissionPO));
    }

    @Override
    public List<Permission> findAll() {
        return permissionMapper.selectList(null)
                .stream()
                .map(PermissionAssembler::toDomain)
                .toList();
    }

    @Override
    public List<Permission> findAllByRoleId(RoleId roleId) {
        return permissionMapper.selectByRoleId(roleId.value())
                .stream()
                .map(PermissionAssembler::toDomain)
                .toList();
    }

    @Override
    public Permission save(Permission permission) {
        return null;
    }

    @Override
    public void deleteById(PermissionId id) {

    }
}
