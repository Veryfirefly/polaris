package com.xsdq.polaris.infrastructure.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 *
 * @author XiaoYu
 * @since 2026/8/4 20:21
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public interface SerializeTag {
}
