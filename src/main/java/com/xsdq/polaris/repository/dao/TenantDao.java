package com.xsdq.polaris.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsdq.polaris.repository.po.TenantPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TenantDao extends BaseMapper<TenantPO> {
}
