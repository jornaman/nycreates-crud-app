package com.nycreates.student;

import com.nycreates.student.config.AppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class MapperArgConverter extends SimpleArgumentConverter {

  @Override
  protected Object convert(Object o, Class<?> clazz) throws ArgumentConversionException {
    try {
      return new AppConfig().objectMapperBuilder().build().readValue(o.toString(), clazz);
    }
    catch (JsonProcessingException e) {
      throw new ArgumentConversionException(
          String.format("Cannot convert %s to %s", o, clazz.getSimpleName()));
    }
  }
}
