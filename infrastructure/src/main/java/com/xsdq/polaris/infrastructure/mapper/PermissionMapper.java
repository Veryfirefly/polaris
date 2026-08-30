package com.xsdq.polaris.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsdq.polaris.infrastructure.persistence.PermissionPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<PermissionPO> {

    List<PermissionPO> selectByRoleId(Long roleId);
}
