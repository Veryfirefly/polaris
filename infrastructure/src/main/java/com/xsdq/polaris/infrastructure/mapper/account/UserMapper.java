package com.xsdq.polaris.infrastructure.mapper.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsdq.polaris.infrastructure.persistence.account.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author XiaoYu
 * @since 2026/8/10 18:00
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {}
