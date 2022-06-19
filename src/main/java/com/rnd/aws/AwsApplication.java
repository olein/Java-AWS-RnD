package com.rnd.aws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rnd.aws.configuration.JacksonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AwsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AwsApplication.class, args);
  }

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    JacksonUtil.configureObjectMapper(objectMapper);
    return objectMapper;
  }
}
