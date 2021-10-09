package com.rnd.aws.elasticache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachingConfiguration extends CachingConfigurerSupport {
  @Override
  public CacheErrorHandler errorHandler() {
    return new CustomCacheErrorHandler();
  }
}
