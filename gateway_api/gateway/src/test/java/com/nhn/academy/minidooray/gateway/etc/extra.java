package com.nhn.academy.minidooray.gateway.etc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.properties.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.config.properties.task.TaskApiServerProperties;
import com.nhn.academy.minidooray.gateway.config.security.SecurityConfig;
import com.nhn.academy.minidooray.gateway.config.webconfig.GatewayApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ContextConfiguration;
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
    assertThat(taskApiServerProperties.getFullUrl()).isEqualTo(taskApiServerProperties.getUrl()+":"+ taskApiServerProperties.getPort());
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

  @Autowired
  ObjectMapper objectMapper;



  @Test
  void getProjectByProjectId() throws JsonProcessingException {

    JsonNode node = restTemplate.getForObject("http://localhost:8081/projects/{projectId}",JsonNode.class,1);

    assertThat(node.get("members").get(0).get("memberId").asText()).isEqualTo("gray");
    assertThat(node.get("projectId").asText()).isEqualTo("1");

  }

  @Test
  void getProjectMembersByProjectId() throws JsonProcessingException {

    JsonNode[] node = restTemplate.getForObject("http://localhost:8081/projects/{projectId}/members",JsonNode[].class,1);

    assertThat(node[0].get("memberId").asText()).isEqualTo("gray");
  }

  @Test
  void createProject() throws JsonProcessingException {

    HttpEntity<String> body = new HttpEntity<>("{\n"
        + "  \"adminId\": \"gray,\",\n"
        + "  \"projectId\": \"10\",\n"
        + "  \"name\": \"test\",\n"
        + "  \"status\": \"ACTIVATE\"\n"
        + "}", null);
    JsonNode node = restTemplate.postForObject("http://localhost:8081/projects",body,JsonNode.class);

    assertThat(node.get("memberId").asText()).isEqualTo("gray");
  }









}
