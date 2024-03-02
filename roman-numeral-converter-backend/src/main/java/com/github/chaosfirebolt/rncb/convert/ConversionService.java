package com.github.chaosfirebolt.rncb.convert;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.chaosfirebolt.converter.MapRomanIntegerCache;
import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.initialization.RomanIntegerArrayInitializationData;
import com.github.chaosfirebolt.converter.api.initialization.source.BasicNumeralsInputSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    RomanInteger.setCache(parserCache -> new MapRomanIntegerCache(parserCache, new CaffeineMapAdapter(caffeineCache), new RomanIntegerArrayInitializationData(new BasicNumeralsInputSource())));
  }

  @Override
  public void destroy() {
    RomanInteger.disableCache();
  }
}
