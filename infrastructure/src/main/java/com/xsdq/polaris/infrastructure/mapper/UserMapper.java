package com.xsdq.polaris.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsdq.polaris.infrastructure.persistence.UserPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author XiaoYu
 * @since 2026/8/10 18:00
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

    List<UserPO> selectByTenantId(Long tenantId);
}
