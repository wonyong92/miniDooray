package com.nhn.academy.minidooray.gateway.config.properties.gateway;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis")
@Getter
@Setter
public class RedisProperties {

  String host;
  String port;
  Integer database;
  String password;
}
