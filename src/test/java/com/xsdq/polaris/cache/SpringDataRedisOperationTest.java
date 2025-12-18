package com.xsdq.polaris.cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringDataRedisOperationTest {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    void testExists() {
        var exists = redisTemplate.hasKey("auth:xiaoyu:mobile:1f7b8a9fc3");
        assertTrue(exists);
    }

    @Test
    void testWriteAndReadCacheFromRedis() {
        Person person = new Person();
        person.setId(1L);
        person.setName("xiaoyu");
        person.setCreateTime(LocalDateTime.now());
        Person.Address address = new Person.Address();
        address.setProvince("四川省");
        address.setCity("成都市");
        address.setCountry("中和镇");

        person.setAddress(address);

        redisTemplate.opsForValue().set("person:xiaoyu", person);
        Person person1 = (Person) redisTemplate.opsForValue().get("person:xiaoyu");
        assertNotNull(person1);
    }

    @Test
    void testWriteAndReadCacheFromRedisUseRecord() {
        AnotherBean bean = new AnotherBean("xiaoyu", "Sichuan province chengdu city", Instant.now().getEpochSecond());
        redisTemplate.opsForValue().set("another:xiaoyu", bean);
        AnotherBean getBean = (AnotherBean) redisTemplate.opsForValue().get("another:xiaoyu");
        assertNotNull(getBean);
    }
}
