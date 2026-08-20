package com.xsdq.polaris.infrastructure.identity.config;

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
}
