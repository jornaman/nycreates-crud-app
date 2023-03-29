package com.nycreates.student.config;

import com.nycreates.student.utility.DateUtil;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class AppConfig {

  @Value("${encryption.secret.key}")
  private String secretKey;

  @Bean
  public String secretKey(){
    return secretKey;
  }

  @Value("${server.port:8080}")
  private int serverPort;

  @Bean
  public int serverPort(){
    return serverPort;
  }

  @Bean
  public Jackson2ObjectMapperBuilder objectMapperBuilder() {
    return new Jackson2ObjectMapperBuilder()
        .createXmlMapper(false)
        .indentOutput(true)
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .serializers(
            new LocalDateSerializer(DateUtil.DATE_FORMAT),
            new LocalDateTimeSerializer(DateUtil.DATE_TIME_FORMAT),
            new LocalTimeSerializer(DateUtil.TIME_FORMAT))
        .deserializers(
            new LocalDateDeserializer(DateUtil.DATE_FORMAT),
            new LocalDateTimeDeserializer(DateUtil.DATE_TIME_FORMAT),
            new LocalTimeDeserializer(DateUtil.TIME_FORMAT))
        .modules(
            new JavaTimeModule(),
            new ParameterNamesModule(),
            new Jdk8Module());
  }

  @Bean
  public MapperFacade mapperFacade(){
    return new DefaultMapperFactory.Builder().build().getMapperFacade();
  }
}
