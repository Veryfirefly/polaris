package com.xsdq.polaris.async.config;

import com.xsdq.polaris.async.autoconfigure.AsyncPoolProperties;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author XiaoYu
 * @since 2026/7/2 15:58
 */
@EnableAsync
@Configuration
@EnableConfigurationProperties(AsyncPoolProperties.class)
public class AsyncConfig implements AsyncConfigurer {

  @Bean({"bizAsyncTaskExecutor"})
  public ThreadPoolTaskExecutor createTaskExecutor(AsyncPoolProperties asyncProps) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(asyncProps.getCorePoolSize());
    executor.setMaxPoolSize(asyncProps.getMaxPoolSize());
    executor.setKeepAliveSeconds(asyncProps.getKeepAliveSeconds());
    executor.setQueueCapacity(asyncProps.getQueueCapacity());
    executor.setThreadNamePrefix(asyncProps.getThreadNamePrefix());
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(15);

    executor.initialize();

    return executor;
  }
}
