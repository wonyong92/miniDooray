package com.nhn.academy.minidooray.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.config.gateway.proterties.RedisProperties;
import com.nhn.academy.minidooray.gateway.domain.gateway.UserDetail;
import com.nhn.academy.minidooray.gateway.util.RestTemplateUtil;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableRedisHttpSession
@ConfigurationPropertiesScan
@Slf4j
public class GatewayApplication {

  public static void main(String[] args) {

    SpringApplication.run(GatewayApplication.class, args);
  }

  @Autowired
  ObjectMapper mapper;

  @Autowired
  AccountApiServerProperties accountApiServerProperties;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  @Primary
  AuthenticationSuccessHandler successHandler() {
    return new AuthenticationSuccessHandler() {
      final RedisTemplate<String, Object> template = redisTemplate(redisConnectionFactory());
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        log.info("formLogin 완료. id: {}", authentication.getName());
        template.opsForHash().put(session.getId(), "username", authentication.getName());
        template.opsForHash().put(session.getId(), "authority", authentication.getAuthorities());

        response.sendRedirect("/");
      }
    };

  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService(restTemplate()));
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public UserDetailsService userDetailsService(RestTemplate restTemplate) {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<String> response = RestTemplateUtil.createQuery(restTemplate, accountApiServerProperties.getUrl(), accountApiServerProperties.getPort(), "/accounts", HttpMethod.GET, String.class, Map.of("id", username));

        String pwd;
        String id;
        try {
          UserDetail userDetail = mapper.readValue(response.getBody(), UserDetail.class);
          pwd = userDetail.getPwd();
          id = userDetail.getId();

          return User.builder().username(id).password(pwd).authorities(new SimpleGrantedAuthority("user")).build();
        } catch (JsonProcessingException e) {
          e.printStackTrace();
          return null;//Todo : custom exception 던지기
        }
      }
    };
  }

  @Autowired
  RedisProperties serverProperties;

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

}
