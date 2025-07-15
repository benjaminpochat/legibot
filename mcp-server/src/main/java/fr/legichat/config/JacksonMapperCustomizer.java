package fr.legichat.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class JacksonMapperCustomizer implements ObjectMapperCustomizer {
  public void customize(ObjectMapper mapper) {
//    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.disable(MapperFeature.REQUIRE_HANDLERS_FOR_JAVA8_TIMES);
//    mapper.registerModule(new JavaTimeModule());
  }
}
