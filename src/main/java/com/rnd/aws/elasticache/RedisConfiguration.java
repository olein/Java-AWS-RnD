package com.rnd.aws.elasticache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCaching
public class RedisConfiguration {

  @Value("${redis.hostname}")
  private String redisHostName;

  @Value("${redis.port}")
  private int redisPort;

  //Standalone Redis connection factory
  //  @Bean
  //  JedisConnectionFactory jedisConnectionFactory() {
  //    RedisStandaloneConfiguration redisStandaloneConfiguration =
  //        new RedisStandaloneConfiguration(redisHostName, redisPort);
  //    return new JedisConnectionFactory(redisStandaloneConfiguration);
  //  }

  List<String> clusterNodes =
      Arrays.asList(
          "node1:6379",
          "node2:6379",
          "node3:6379");

  @Bean
  RedisConnectionFactory connectionFactory() {
    return new JedisConnectionFactory(new RedisClusterConfiguration(clusterNodes));
  }

  @Bean(value = "redisTemplate")
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setConnectionFactory(factory);
    return template;
  }

  @Primary
  @Bean(name = "cacheManager1Hr") // Default cache manager is 1 hr
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    Duration expiration = Duration.ofHours(1);
    RedisCacheManager redisCacheManager =
        RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(
                RedisCacheConfiguration.defaultCacheConfig()
                    .disableCachingNullValues()
                    .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            new GenericJackson2JsonRedisSerializer()))
                    .entryTtl(expiration))
            .build();

    redisCacheManager.setTransactionAware(false);
    return redisCacheManager;
  }

  @Bean(name = "cacheManager1Minutes")
  public CacheManager cacheManager1Minutes(RedisConnectionFactory redisConnectionFactory) {
    Duration expiration = Duration.ofMinutes(1);
    RedisCacheManager redisCacheManager =
        RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(
                RedisCacheConfiguration.defaultCacheConfig()
                    .disableCachingNullValues()
                    .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            new GenericJackson2JsonRedisSerializer()))
                    .entryTtl(expiration))
            .build();

    redisCacheManager.setTransactionAware(false);
    return redisCacheManager;
  }
}
