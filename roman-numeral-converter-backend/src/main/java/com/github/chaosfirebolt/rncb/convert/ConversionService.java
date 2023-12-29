package com.github.chaosfirebolt.rncb.convert;

import com.github.chaosfirebolt.converter.BiDirectionalMapRomanIntegerCache;
import com.github.chaosfirebolt.converter.RomanInteger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class ConversionService implements InitializingBean, DisposableBean {

  public RomanInteger convert(ConversionValue value) {
    try {
      return RomanInteger.parse(value.value());
    } catch (IllegalArgumentException | NullPointerException exc) {
      throw new ConversionException(exc.getMessage(), exc);
    }
  }

  @Override
  public void afterPropertiesSet() {
    RomanInteger.setCache(BiDirectionalMapRomanIntegerCache::new);
  }

  @Override
  public void destroy() {
    RomanInteger.disableCache();
  }
}
