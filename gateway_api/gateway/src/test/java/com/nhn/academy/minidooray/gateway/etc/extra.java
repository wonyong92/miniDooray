package com.nhn.academy.minidooray.gateway.etc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nhn.academy.minidooray.gateway.config.properties.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.config.properties.task.TaskApiServerProperties;
import com.nhn.academy.minidooray.gateway.config.security.SecurityConfig;
import com.nhn.academy.minidooray.gateway.config.webconfig.GatewayApplication;
import java.io.ObjectInputFilter.Config;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@ContextConfiguration(classes = {GatewayApplication.class,SecurityConfig.class},initializers = ConfigDataApplicationContextInitializer.class)
class extra {

  @Autowired
  AccountApiServerProperties accountApiServerProperties;
  @Autowired
  TaskApiServerProperties taskApiServerProperties;
  @Test
  void configurationProperties_getFullUrl_test (){
    assertThat(accountApiServerProperties.getFullUrl()).isEqualTo(accountApiServerProperties.getUrl()+":"+accountApiServerProperties.getPort());
    assertThat(taskApiServerProperties.getFullUrl()).isEqualTo(taskApiServerProperties.getUrl()+":"+taskApiServerProperties.getPort());
  }


  @Autowired
  RedisTemplate<String,Object> redisTemplate;

  @Test
  void redisSession_opsForHash_get_put_delete_test(){
    assertThat(redisTemplate.opsForHash().get("redis_test","test_key")).isEqualTo(null);
    redisTemplate.opsForHash().put("redis_test","test_key","test_value");
    assertThat(redisTemplate.opsForHash().get("redis_test","test_key")).isEqualTo("test_value");
    redisTemplate.opsForHash().delete("redis_test","test_key");
    assertThat(redisTemplate.opsForHash().get("redis_test","test_key")).isEqualTo(null);
  }

  @Autowired
  RestTemplate restTemplate;

  @Test
  void restTemplate_test(){

  }


}
