package com.xsdq.polaris.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsdq.polaris.infrastructure.persistence.MenuPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author XiaoYu
 * @since 2026/8/10 18:04
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuPO> {}
