package com.rnd.aws.elasticache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil<T> {

  @Autowired private RedisTemplate<String, Object> redisTemplate;

  public long getNextId(String redisKey, long initVal) {
    Boolean hasKey = redisTemplate.hasKey(redisKey);

    if (hasKey != null && !hasKey) {
      redisTemplate.opsForValue().set(redisKey, initVal);
      return initVal;
    }
    Long increment = redisTemplate.opsForValue().increment(redisKey);
    return increment != null ? increment : 0L;
  }

  public void deleteKey(String redisKey) {
    Boolean hasKeyBoolean = redisTemplate.hasKey(redisKey);
    boolean hasKey = hasKeyBoolean != null && hasKeyBoolean;

    if (hasKey) {
      redisTemplate.delete(redisKey);
    }
  }

  public void setValue(String redisKey, String value) {
    redisTemplate.opsForValue().set(redisKey, value);
  }

  public Object getValue(String redisKey) {
    return redisTemplate.opsForValue().get(redisKey);
  }

  public List<String> getKeys() {
    List<String> keys = new ArrayList<>();
    for (String key : redisTemplate.keys("*")) {
      keys.add(key);
    }
    return keys;
  }

  public Boolean setWithTimeOut(String key, String value, long timeout, TimeUnit unit) {
    return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
  }
}
