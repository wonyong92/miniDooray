package com.nhn.academy.minidooray.gateway.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GatewayApplicationTests {

  @Test
  void contextLoads() {
    System.out.println(System.getProperty("spring.profiles.active"));
  }

}
