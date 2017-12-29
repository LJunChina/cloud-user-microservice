package com.cloud.user.microservice.config;

import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import com.cloud.user.microservice.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.UUID;

public class RedisTest extends UserMicroserviceApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStringRedis(){
        String key = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(key,"JunChina");
        Assert.assertEquals("JunChina",stringRedisTemplate.opsForValue().get(key));
    }

    @Test
    public void testObjectRedis() throws Exception{
        User user = new User();
        user.setName("LJunChina");
        user.setId(UUID.randomUUID().toString());
        String key = UUID.randomUUID().toString().toUpperCase();
        ValueOperations valueOperations =  this.redisTemplate.opsForValue();
        valueOperations.set(key,user);
        Thread.sleep(3000);
        User value = (User) this.redisTemplate.opsForValue().get(key);
        Assert.assertEquals("LJunChina",value.getName());
    }
}
