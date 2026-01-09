package com.xsdq.polaris.repository.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsdq.polaris.repository.po.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author XiaoYu
 * @since 2025/12/23 17:08
 */
@Mapper
public interface RoleDao extends BaseMapper<RolePO> {

	List<RolePO> findRolesByUserId(long userId);
}
