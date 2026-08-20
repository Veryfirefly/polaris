package com.xsdq.polaris.account.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsdq.polaris.account.infrastructure.persistence.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author XiaoYu
 * @since 2026/8/10 18:00
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {}
