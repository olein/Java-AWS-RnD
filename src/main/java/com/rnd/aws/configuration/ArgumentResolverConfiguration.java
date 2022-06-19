package com.rnd.aws.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@Configuration
@ComponentScan
public class ArgumentResolverConfiguration implements WebMvcConfigurer {

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    for (HttpMessageConverter<?> converter : converters) {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        MappingJackson2HttpMessageConverter conv = (MappingJackson2HttpMessageConverter) converter;
        JacksonUtil.configureObjectMapper(conv.getObjectMapper());
      } else if (converter instanceof MappingJackson2XmlHttpMessageConverter) {
        MappingJackson2XmlHttpMessageConverter conv =
            (MappingJackson2XmlHttpMessageConverter) converter;
        JacksonUtil.configureObjectMapper(conv.getObjectMapper());
      }
    }

    WebMvcConfigurer.super.extendMessageConverters(converters);
  }
}
