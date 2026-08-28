package com.xsdq.polaris.infrastructure.identity.config;

import com.xsdq.polaris.domain.factory.TenantIdGenerator;
import com.xsdq.polaris.domain.factory.UserIdGenerator;
import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.domain.model.user.UserId;
import com.xsdq.polaris.infrastructure.identity.IdentityGenerator;
import com.xsdq.polaris.infrastructure.identity.SnowflakeIdentityGenerator;
import com.xsdq.polaris.infrastructure.identity.VariableLengthHashIdentityGenerator;
import com.xsdq.polaris.infrastructure.identity.VariableLengthIdentityGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XiaoYu
 * @since 2026/8/20 14:43
 */
@Configuration
public class IdentityGeneratorConfig {

  @Bean
  public VariableLengthIdentityGenerator<String> variableLengthIdentityGenerator() {
    return new VariableLengthHashIdentityGenerator();
  }

  @Bean
  public TenantIdGenerator tenantIdGenerator() {
    return new TenantIdGenerator() {

      private final IdentityGenerator<Long> idGenerator = new SnowflakeIdentityGenerator(1L, 1L);

      @Override
      public TenantId generate() {
        return TenantId.of(idGenerator.generate().value());
      }
    };
  }

  @Bean
  public UserIdGenerator userIdGenerator() {
    return new UserIdGenerator() {

      private final IdentityGenerator<Long> idGenerator = new SnowflakeIdentityGenerator(1L, 2L);

      @Override
      public UserId generate() {
        return UserId.of(idGenerator.generate().value());
      }
    };
  }
}
