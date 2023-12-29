package com.github.chaosfirebolt.rncb.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConversionValueConverter implements Converter<String, ConversionValue> {

  @Override
  public ConversionValue convert(String source) {
    return new ConversionValue(source);
  }
}
