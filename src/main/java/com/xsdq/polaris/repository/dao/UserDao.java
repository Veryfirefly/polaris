package com.xsdq.polaris.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsdq.polaris.repository.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author XiaoYu
 * @since 2025/12/23 17:01
 */
@Mapper
public interface UserDao extends BaseMapper<UserPO> {

	UserPO findByAccount(String account);
}
