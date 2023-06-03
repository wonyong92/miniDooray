package com.nhn.academy.minidooray.gateway;

import static com.nhn.academy.minidooray.gateway.security.filter.JwtProperties.TOKEN_PREFIX;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nhn.academy.minidooray.gateway.config.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.config.gateway.proterties.RedisProperties;
import com.nhn.academy.minidooray.gateway.domain.gateway.UserDetail;
import com.nhn.academy.minidooray.gateway.security.filter.JwtAuthorizationFilter;
import com.nhn.academy.minidooray.gateway.security.filter.JwtProperties;
import com.nhn.academy.minidooray.gateway.service.account.AccountService;
import com.nhn.academy.minidooray.gateway.util.RestTemplateUtil;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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

//  @Bean
//  public AuthenticationSuccessHandler loginSuccessHandler(RedisTemplate<String, Object> redisTemplate) {
//    return new OauthLoginSuccessHandler(redisTemplate);
//  }

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

        String accessToken = JWT.create().withClaim("id",authentication.getName()).sign(Algorithm.HMAC512(JwtProperties.SECRET));


        Cookie accessJwt = new Cookie("ACCESS_TOKEN", TOKEN_PREFIX+accessToken);
        accessJwt.setDomain("localhost");
        accessJwt.setPath("/");

        Cookie refreshJwt = new Cookie("REFRESH_TOKEN","testrefreshtoken");
        refreshJwt.setDomain("localhost");
        refreshJwt.setPath("/");
        refreshJwt.setHttpOnly(true);
        refreshJwt.setMaxAge(3600);
        response.addCookie(accessJwt);
        response.addCookie(refreshJwt);

        Cookie cookie = new Cookie("SESSION", session.getId());
        cookie.setMaxAge(3600);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

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

//        return User.builder().username("test").password(passwordEncoder.encode("test")).authorities(new SimpleGrantedAuthority("user")).build();
      }
    };
  }


//  @Bean
//  @Primary
//  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//    JpaTransactionManager transactionManager = new JpaTransactionManager();//JPA용 트랜잭션 매니저 사용
//    transactionManager.setEntityManagerFactory(entityManagerFactory);
//
//    return transactionManager;
//  }

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

//  @Bean
//  JwtAuthorizationFilter jwtAuthorizationFilter(ObjectMapper mapper, AccountService service) {
//    return new JwtAuthorizationFilter(service, mapper);
//  }

}
