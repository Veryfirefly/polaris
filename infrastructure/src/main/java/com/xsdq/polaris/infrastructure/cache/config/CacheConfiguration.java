package com.xsdq.polaris.infrastructure.cache.config;

import java.io.Serializable;
import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nonnull;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * @author XiaoYu
 * @since 2026/8/4 20:17
 */
@EnableCaching
@Configuration
public class CacheConfiguration {

	private final RedisConnectionFactory connectionFactory;

	public CacheConfiguration(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Bean
	public CacheManager cacheManager() {
		ObjectMapper objectMapper = createCachingObjectMapper();
		GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofDays(1))
				.computePrefixWith(new SimpleCacheKeyPrefix())
				.disableCachingNullValues()
				.serializeKeysWith(RedisSerializationContext.string().getKeySerializationPair())
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(configuration)
				.build();
	}

	@Bean
	public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
		ObjectMapper objectMapper = createCachingObjectMapper();
		StringRedisSerializer keySerializer = new StringRedisSerializer();
		GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);

		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setValueSerializer(valueSerializer);
		redisTemplate.setHashKeySerializer(keySerializer);
		redisTemplate.setHashValueSerializer(valueSerializer);
		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	private ObjectMapper createCachingObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}

	static class SimpleCacheKeyPrefix implements CacheKeyPrefix {

		private static final String SEPARATOR = ":";

		@Nonnull
		@Override
		public String compute(@Nonnull String cacheName) {
			return cacheName + SEPARATOR;
		}
	}
}
