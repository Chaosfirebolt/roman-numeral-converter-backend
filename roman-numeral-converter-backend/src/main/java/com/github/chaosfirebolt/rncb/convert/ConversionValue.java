package com.github.chaosfirebolt.rncb.convert;

public record ConversionValue(String value) {

  public String value() {
    if (value == null || value.trim().isEmpty()) {
      throw new ConversionException("Missing value to convert");
    }
    return value;
  }
}
