package com.github.chaosfirebolt.rncb.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.chaosfirebolt.converter.RomanInteger;

import java.io.IOException;

class RomanIntegerSerializer extends StdSerializer<RomanInteger> {

  RomanIntegerSerializer() {
    super(RomanInteger.class);
  }

  @Override
  public void serialize(RomanInteger value, JsonGenerator generator, SerializerProvider provider) throws IOException {
    generator.writeStartObject();
    generator.writeStringField("r", value.getRoman());
    generator.writeNumberField("a", value.getArabic());
    generator.writeEndObject();
  }
}
