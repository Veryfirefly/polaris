package com.xsdq.polaris.infrastructure.repository;

import com.xsdq.polaris.domain.model.tenant.Tenant;
import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.domain.repository.TenantRepository;
import com.xsdq.polaris.infrastructure.mapper.TenantMapper;
import com.xsdq.polaris.infrastructure.persistence.TenantPO;
import com.xsdq.polaris.infrastructure.persistence.assembler.TenantAssembler;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TenantRepositoryImpl implements TenantRepository {

    private final TenantMapper tenantMapper;

    public TenantRepositoryImpl(TenantMapper tenantMapper) {
        this.tenantMapper = tenantMapper;
    }

    @Override
    public Optional<Tenant> findById(TenantId id) {
        TenantPO tenantPO = tenantMapper.selectById(id.value());
        if (tenantPO == null)
            return Optional.empty();
        return Optional.of(TenantAssembler.toDomain(tenantPO));
    }

    @Override
    public List<Tenant> findAll() {
        return tenantMapper.selectList(null)
                .stream()
                .map(TenantAssembler::toDomain)
                .toList();
    }

    @Override
    public Tenant save(Tenant tenant) {
        return null;
    }

    @Override
    public void deleteById(TenantId id) {

    }
}
