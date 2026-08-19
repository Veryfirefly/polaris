package com.xsdq.polaris.cache;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@SuppressWarnings("unchecked")
@Component
public class RedisCacheService {

	private final RedisTemplate<String, Serializable> redisTemplate;

	public RedisCacheService(RedisTemplate<String, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public <T extends Serializable> T get(String key) {
		return (T) redisTemplate.opsForValue().get(key);
	}

	public <V extends Serializable> void put(String key, V value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public <V extends Serializable> void put(String key, V value, long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	public <V extends Serializable> void put(String key, V value, Duration duration) {
		redisTemplate.opsForValue().set(key, value, duration);
	}

	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	public <V extends Serializable> List<V> mutiGet(String pattern) {
		Set<String> keys = keys(pattern);
		if (CollectionUtils.isEmpty(keys))
			return List.of();

		return mutiGet(keys);
	}

	public <V extends Serializable> List<V> mutiGet(Set<String> keys) {
		List<V> values = new ArrayList<>(keys.size());
		List<Serializable> mutiGetValues = redisTemplate.opsForValue().multiGet(keys);
		if (mutiGetValues == null)
			return List.of();

		for (Serializable mutiGetValue : mutiGetValues) {
			values.add((V) mutiGetValue);
		}
		return values;
	}

	public boolean containsKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * <p>key不存在时, 插入成功, 返回null.</p>
	 * <p>key已存在时, 不操作, 返回原来的旧值.</p>
	 */
	public <V extends Serializable> V putIfAbsent(String key, V value, long timeout, TimeUnit unit) {
		ValueOperations<String, Serializable> operations = redisTemplate.opsForValue();
		Serializable oldValue = operations.get(key);
		if (oldValue == null) {
			operations.set(key, value, timeout, unit);
			return null;
		}
		return (V) oldValue;
	}

	public boolean evict(String key) {
		return redisTemplate.delete(key);
	}

	public void expire(String key, long timeout, TimeUnit unit) {
		redisTemplate.expire(key, timeout, unit);
	}
}
