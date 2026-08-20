package com.xsdq.polaris.cache;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SpringBootTest
class SpringDataRedisOperationTest {

  @Autowired private RedisTemplate<String, Serializable> redisTemplate;

  @Autowired private PersonRepository personRepository;

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
    AnotherBean bean =
        new AnotherBean("xiaoyu", "Sichuan province chengdu city", Instant.now().getEpochSecond());
    redisTemplate.opsForValue().set("another:xiaoyu", bean);
    AnotherBean getBean = (AnotherBean) redisTemplate.opsForValue().get("another:xiaoyu");
    assertNotNull(getBean);
  }

  @Test
  void testCrudRepository() {
    Person person = new Person();
    person.setId(1L);
    person.setTenantId(UUID.randomUUID().toString());
    person.setName("xiaoyu");
    //        person.setUniqueId(new CompositeKey(person.getTenantId(), person.getName()));
    person.setCreateTime(LocalDateTime.now());
    person.setAddress(new Person.Address());
    person.setAuthorities(
        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER")));
    person.setTtl(60);

    personRepository.save(person);

    //        Optional<Person> needPerson = personRepository.findById("xiaoyu");
    //        assertTrue(needPerson.isPresent());
    //        assertFalse(needPerson.get().getAuthorities().isEmpty());
  }

  @Test
  void testDerivedCountMethodByCrudRepository() {
    Random random = new Random();
    List<Person> persons = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Person person = new Person();
      person.setId(random.nextLong(1L, 99999L));
      person.setTenantId(UUID.randomUUID().toString().replace("-", ""));
      person.setName(randomName(random));
      person.setCreateTime(LocalDateTime.now());
      person.setAddress(new Person.Address());
      person.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
      person.setTtl(random.nextLong(60, 180));

      persons.add(person);
    }

    personRepository.saveAll(persons);
  }

  String randomName(Random random) {
    Random ran = random == null ? new Random() : random;
    int nameLength = ran.nextInt(3, 10);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < nameLength; i++) {
      int ascii = ran.nextInt(65, 90); // A-Z
      sb.append((char) ascii);
    }
    return sb.toString();
  }
}
