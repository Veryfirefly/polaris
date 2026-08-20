package com.xsdq.polaris.http.whois;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WhoisInfoQueryTest {

  @Autowired private WhoisInfoQuery whoisInfoQuery;

  private final ExpectedAnswer[] answers = {
    new ExpectedAnswer("182.150.24.251", "中国", "四川省", "成都市")
  };

  @Test
  void testIp2RegionWhoisInfoQuery() throws Exception {
    for (ExpectedAnswer answer : answers) {
      WhoisInfo whoisInfo = whoisInfoQuery.getWhoisInfo(answer.ip());
      assertAll(
          () -> assertNotNull(whoisInfo),
          () -> assertEquals(answer.country(), whoisInfo.country()),
          () -> assertEquals(answer.province(), whoisInfo.province()),
          () -> assertEquals(answer.city(), whoisInfo.city()),
          () -> assertEquals("电信", whoisInfo.isp()));
    }
  }

  record ExpectedAnswer(String ip, String country, String province, String city) {}
}
