package com.xsdq.polaris.cache;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 标记类, 因Jackson只对<strong>non-final class</strong>或<strong>non-final field</strong>中包含的java类型做记录,
 * 若redis serializer要对record、或final类做序列化写入支持, 则要继承该接口
 *
 * @author XiaoYu
 * @since 2026/5/29 17:53
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public interface Cacheable extends Serializable {

}
