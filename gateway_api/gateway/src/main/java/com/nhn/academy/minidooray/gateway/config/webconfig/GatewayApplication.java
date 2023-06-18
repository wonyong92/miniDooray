package com.nhn.academy.minidooray.gateway.config.webconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.ProjectBase;
import com.nhn.academy.minidooray.gateway.config.properties.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.config.properties.gateway.RedisProperties;
import com.nhn.academy.minidooray.gateway.config.properties.task.TaskApiServerProperties;
import com.nhn.academy.minidooray.gateway.interceptor.AuthorityCheckerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableTransactionManagement
@EnableRedisHttpSession
@Slf4j
@ConfigurationPropertiesScan(basePackageClasses = ProjectBase.class)
@ComponentScan(basePackageClasses = ProjectBase.class)
public class GatewayApplication implements WebMvcConfigurer, ApplicationContextAware {

  @Autowired
  ObjectMapper mapper;
  @Autowired
  AccountApiServerProperties accountApiServerProperties;
  @Autowired
  RedisProperties serverProperties;
  @Autowired
  TaskApiServerProperties taskApiServerProperties;
  ApplicationContext context;

  public static void main(String[] args) {

    SpringApplication.run(GatewayApplication.class, args);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  CloseableHttpClient httpClient() {
    return HttpClientBuilder.create()
        .setMaxConnTotal(100)    //최대 오픈되는 커넥션 수, 연결을 유지할 최대 숫자
        .setMaxConnPerRoute(30)   //IP, 포트 1쌍에 대해 수행할 커넥션 수, 특정 경로당 최대 숫자
        .build();
  }

  @Bean
  public RestTemplate restTemplate() {
    //rest template 동작 방식의 차이 발생 - connection pool 이용 vs socket 이용
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setConnectTimeout(3000);
    requestFactory.setConnectionRequestTimeout(3000);
    requestFactory.setReadTimeout(3000);
    requestFactory.setHttpClient(httpClient());
    return new RestTemplate(requestFactory);
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(serverProperties.getHost());
    redisStandaloneConfiguration.setPassword(serverProperties.getPassword()); //redis에 비밀번호가 설정 되어 있는 경우 설정해주면 됩니다.
    redisStandaloneConfiguration.setDatabase(serverProperties.getDatabase());
    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

    return redisTemplate;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authorityChecker());
  }

  @Bean
  public AuthorityCheckerInterceptor authorityChecker() {
    return new AuthorityCheckerInterceptor(restTemplate(), accountApiServerProperties, taskApiServerProperties, context.getBean(ObjectMapper.class));
  }


  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }
}
