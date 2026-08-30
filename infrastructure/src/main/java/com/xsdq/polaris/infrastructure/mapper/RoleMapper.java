package com.xsdq.polaris.infrastructure.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsdq.polaris.infrastructure.persistence.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author XiaoYu
 * @since 2026/8/10 18:02
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO> {

	List<RolePO> selectByUserId(Long userId);

    List<RolePO> selectByTenantId(Long tenantId);
}
