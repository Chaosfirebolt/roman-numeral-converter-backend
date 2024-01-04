package com.github.chaosfirebolt.rncb.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.chaosfirebolt.generator.identifier.api.IdentifierGenerator;
import com.github.chaosfirebolt.generator.identifier.api.string.RandomUuidStringIdentifierGenerator;
import com.github.chaosfirebolt.generator.identifier.api.string.builders.StringGeneratorBuilders;
import com.github.chaosfirebolt.rncb.config.filter.ConversionThrottlingFilter;
import com.github.chaosfirebolt.rncb.config.filter.RegistrationThrottlingFilter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;

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

  @Bean("appClock")
  public Clock applicationClock() {
    return Clock.systemUTC();
  }

  @Bean
  public FilterRegistrationBean<RegistrationThrottlingFilter> registrationThrottlingFilter() {
    //TODO finish!!!
    FilterRegistrationBean<RegistrationThrottlingFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(null);
    registrationBean.addUrlPatterns("/app/register");

    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<ConversionThrottlingFilter> conversionThrottlingFilter() {
    //TODO finish!!!
    FilterRegistrationBean<ConversionThrottlingFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(null);
    registrationBean.addUrlPatterns("/convert");

    return registrationBean;
  }
}
