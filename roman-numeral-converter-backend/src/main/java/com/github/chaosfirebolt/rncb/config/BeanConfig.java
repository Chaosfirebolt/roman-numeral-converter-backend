package com.github.chaosfirebolt.rncb.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.chaosfirebolt.generator.identifier.api.IdentifierGenerator;
import com.github.chaosfirebolt.generator.identifier.api.string.RandomUuidStringIdentifierGenerator;
import com.github.chaosfirebolt.generator.identifier.api.string.builders.StringGeneratorBuilders;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> builder
            .serializers(new RomanIntegerSerializer())
            .serializationInclusion(JsonInclude.Include.NON_ABSENT);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean("appIdGenerator")
  public IdentifierGenerator<String> appIdGen() {
    return new RandomUuidStringIdentifierGenerator();
  }

  @Bean("appPasswordGenerator")
  public IdentifierGenerator<String> appPassGen() {
    return StringGeneratorBuilders.anyCharacterIdentifierGeneratorBuilder()
            .setUpperCaseLength(10)
            .setLowerCaseLength(11)
            .setNumericLength(7)
            .setSpecialCharacterLength(3)
            .build();
  }
}
