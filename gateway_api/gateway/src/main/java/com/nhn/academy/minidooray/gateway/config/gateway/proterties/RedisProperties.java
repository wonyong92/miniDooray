package com.nhn.academy.minidooray.gateway.config.gateway.proterties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis")
@Getter
@Setter
public class RedisProperties {
/*



  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private int redisPort;

  @Value("${spring.redis.password}")
  private String redisPwd;

  @Value("${spring.redis.database}")
  private int redisDb;

*/

  String host;
  String port;
  Integer database;
  String password;
}
