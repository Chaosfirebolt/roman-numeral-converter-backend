package com.github.chaosfirebolt.rncb.convert;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.chaosfirebolt.converter.MapRomanIntegerCache;
import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.initialization.InitializationData;
import com.github.chaosfirebolt.converter.api.initialization.RomanIntegerArrayInitializationData;
import com.github.chaosfirebolt.converter.api.initialization.source.BasicNumeralsInputSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConversionService implements InitializingBean, DisposableBean {

  private final Cache<String, RomanInteger> caffeineCache;

  @Autowired
  public ConversionService(Cache<String, RomanInteger> caffeineCache) {
    this.caffeineCache = caffeineCache;
  }

  public RomanInteger convert(ConversionValue value) {
    try {
      return RomanInteger.parse(value.value());
    } catch (IllegalArgumentException | NullPointerException exc) {
      throw new ConversionException(exc.getMessage(), exc);
    }
  }

  @Override
  public void afterPropertiesSet() {
    InitializationData<Map<String, RomanInteger>> initializationData =  new RomanIntegerArrayInitializationData(new BasicNumeralsInputSource());
    RomanInteger.setCache(parserCache -> new MapRomanIntegerCache(parserCache, new CaffeineMapAdapter(caffeineCache), initializationData));
  }

  @Override
  public void destroy() {
    RomanInteger.disableCache();
  }
}
