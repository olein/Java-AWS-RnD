package com.rnd.aws.elasticache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

  private static final Logger LOG = LoggerFactory.getLogger(CacheService.class);

  @Cacheable(value = "CACHE", key = "{#name}", cacheManager = "cacheManager1Hr")
  public String cacheMethod(String name) {
    LOG.info("Method cached " + name);
    return "This method will cache the result " + name;
  }

  @Caching(evict = {@CacheEvict(cacheNames = "CACHE", key = "{#name}")})
  public String clearCache(String name) {
    LOG.info("Cache cleared " + name);
    return "This method will clear cache " + name;
  }
}
