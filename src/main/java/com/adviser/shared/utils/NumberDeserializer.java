package com.adviser.shared.utils;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class NumberDeserializer extends JsonDeserializer<Object> {

  @Override
  public Object deserialize(final JsonParser jp, final DeserializationContext ctxt) {
    try {
      return Long.parseLong(jp.getText());
    } catch (Exception e0) {
      try {
        return new Float(jp.getText()).longValue();
      } catch (Exception e1) {
        return 0;
      }
    }
  }

}
