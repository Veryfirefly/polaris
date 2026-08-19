package com.xsdq.polaris.infrastructure.async.autoconfigure;

import lombok.Getter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 *
 * @author XiaoYu
 * @since 2026/8/4 20:12
 */
@Getter
@ToString
@ConfigurationProperties(prefix = "polaris.async")
public class AsyncPoolProperties {

	private final int corePoolSize;
	private final int maxPoolSize;
	private final String threadNamePrefix;
	private final int keepAliveSeconds;
	private final int queueCapacity;

	@ConstructorBinding
	public AsyncPoolProperties(
			@DefaultValue("1") int corePoolSize,
			@DefaultValue("2") int maxPoolSize,
			@DefaultValue("biz-async-") String threadNamePrefix,
			@DefaultValue("60") int keepAliveSeconds,
			@DefaultValue("128") int queueCapacity) {
		this.corePoolSize = corePoolSize;
		this.maxPoolSize = maxPoolSize;
		this.threadNamePrefix = threadNamePrefix;
		this.keepAliveSeconds = keepAliveSeconds;
		this.queueCapacity = queueCapacity;
	}
}
