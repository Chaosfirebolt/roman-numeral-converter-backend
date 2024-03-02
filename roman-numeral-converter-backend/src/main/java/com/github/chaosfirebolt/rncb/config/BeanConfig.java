package com.github.chaosfirebolt.rncb.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.initialization.source.BasicNumeralsInputSource;
import com.github.chaosfirebolt.converter.api.initialization.source.InputSource;
import com.github.chaosfirebolt.generator.identifier.api.IdentifierGenerator;
import com.github.chaosfirebolt.generator.identifier.api.string.RandomUuidStringIdentifierGenerator;
import com.github.chaosfirebolt.generator.identifier.api.string.builders.StringGeneratorBuilders;
import com.github.chaosfirebolt.rncb.config.filter.ConversionThrottlingFilter;
import com.github.chaosfirebolt.rncb.config.filter.RegistrationThrottlingFilter;
import com.github.chaosfirebolt.rncb.convert.AppClockTicker;
import com.github.chaosfirebolt.rncb.convert.CaffeineExpiry;
import com.github.chaosfirebolt.rncb.storage.RequestStorage;
import com.github.chaosfirebolt.rncb.storage.time.HourRange;
import com.github.chaosfirebolt.rncb.storage.time.MinuteRange;
import com.github.chaosfirebolt.rncb.storage.time.TimeRangeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.time.Duration;
import java.util.List;

@Configuration
@EnableScheduling
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
  @Autowired
  public FilterRegistrationBean<RegistrationThrottlingFilter> registrationThrottlingFilter(Clock appClock, @Qualifier("reg-storage") RequestStorage requestStorage,
                                                                                           List<TimeRangeFactory> factories, ObjectMapper mapper) {
    FilterRegistrationBean<RegistrationThrottlingFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new RegistrationThrottlingFilter(appClock, requestStorage, factories, mapper));
    registrationBean.addUrlPatterns("/app/register");

    return registrationBean;
  }

  @Bean
  @Autowired
  public FilterRegistrationBean<ConversionThrottlingFilter> conversionThrottlingFilter(Clock appClock, @Qualifier("app-storage") RequestStorage requestStorage,
                                                                                       List<TimeRangeFactory> factories, ObjectMapper mapper) {
    FilterRegistrationBean<ConversionThrottlingFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new ConversionThrottlingFilter(appClock, requestStorage, factories, mapper));
    registrationBean.addUrlPatterns("/convert");

    return registrationBean;
  }

  @Bean
  public List<TimeRangeFactory> timeRangeFactories() {
    return List.of(MinuteRange::new, HourRange::new);
  }

  @Bean
  public Cache<String, RomanInteger> caffeine(Clock appClock) {
    InputSource<RomanInteger[]> basicNumerals = new BasicNumeralsInputSource();
    Ticker ticker = new AppClockTicker(appClock);
    return Caffeine.newBuilder()
            .maximumSize(5_000)
            .ticker(ticker)
            .expireAfter(new CaffeineExpiry(Duration.ofSeconds(15), ticker, basicNumerals.getInputData()))
            .build();
  }
}
