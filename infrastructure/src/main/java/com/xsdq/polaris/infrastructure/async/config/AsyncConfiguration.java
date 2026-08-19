package com.xsdq.polaris.infrastructure.async.config;

import java.util.concurrent.ThreadPoolExecutor;


import com.xsdq.polaris.infrastructure.async.autoconfigure.AsyncPoolProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author XiaoYu
 * @since 2026/8/4 20:11
 */
@EnableAsync
@Configuration
@EnableConfigurationProperties(AsyncPoolProperties.class)
public class AsyncConfiguration {

	@Bean({"bizAsyncTaskExecutor"})
	public ThreadPoolTaskExecutor createTaskExecutor(AsyncPoolProperties asyncPoolProps) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(asyncPoolProps.getCorePoolSize());
		executor.setMaxPoolSize(asyncPoolProps.getMaxPoolSize());
		executor.setKeepAliveSeconds(asyncPoolProps.getKeepAliveSeconds());
		executor.setQueueCapacity(asyncPoolProps.getQueueCapacity());
		executor.setThreadNamePrefix(asyncPoolProps.getThreadNamePrefix());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(15);

		executor.initialize();

		return executor;
	}
}
