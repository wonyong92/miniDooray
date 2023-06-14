package com.nhn.academy.minidooray.gateway.config.security;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.properties.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.security.details.service.CustomOAuth2MemberService;
import com.nhn.academy.minidooray.gateway.security.handler.OauthLoginSuccessHandler;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity(debug = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@Slf4j
public class SecurityConfig {

  @Bean
  protected SecurityFilterChain configTest(HttpSecurity http, OauthLoginSuccessHandler oauthLoginSuccessHandler, CustomOAuth2MemberService oAuth2MemberService, RestTemplate restTemplate, PasswordEncoder passwordEncoder, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper, AccountApiServerProperties accountApiServerProperties)
      throws Exception {

    http.csrf().disable();
    http.anonymous().disable();

    http.authenticationProvider(authenticationProvider(passwordEncoder, restTemplate, objectMapper, accountApiServerProperties))
        .authorizeRequests()
        .antMatchers("/login/*")
        .permitAll()
        .antMatchers("/")
        .permitAll()
        .antMatchers(HttpMethod.POST,"/account/*")
        .permitAll()
        .anyRequest().permitAll();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

    http.rememberMe()
        .rememberMeServices(rememberMeServices(userDetailsService(restTemplate, objectMapper, accountApiServerProperties)));

    http.formLogin()
        .loginProcessingUrl("/login")
        .successHandler(new AuthenticationSuccessHandler() {
                          final RedisTemplate<String, Object> template = redisTemplate;

                          @Override
                          public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                            HttpSession session = request.getSession(true);
                            log.info("formLogin 완료. id: {}", authentication.getName());
                            template.opsForHash().put(session.getId(), "username", authentication.getName());
                            template.opsForHash().put(session.getId(), "authority", authentication.getAuthorities());
                            response.sendRedirect("/");
                          }
                        }
        )
    ;

    http.logout()
        .invalidateHttpSession(true)
        .logoutUrl("/logout")
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID", "SESSIONID", "SESSION", "ACCESS_TOKEN", "REFRESH_TOKEN")
        .logoutSuccessUrl("/login");

    http.oauth2Login()
        /*oauth 로그인 수행 지점 설정*/
        .authorizationEndpoint()
        .baseUri("/login")
        .and()
        .successHandler(oauthLoginSuccessHandler)
        .defaultSuccessUrl("/") //successHandler 를 덮어쓴다
        .userInfoEndpoint()
        .userService(oAuth2MemberService);

    http.headers()
        .defaultsDisabled()
        .contentTypeOptions()
        .and()
        .cacheControl()
        .and()
        .xssProtection().block(true)
        .and()
        .frameOptions().sameOrigin()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(new AccessDeniedExceptionHandler());
//    보안 헤더 설정

    return http.build();
  }

//  @Bean
//  public CookieSerializer cookieSerializer() {
//    DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//    serializer.setCookieName("SESSIONID");
//    serializer.setCookiePath("/");
//    serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
//    return serializer;
//  }

  @Bean
  public TokenBasedRememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
    TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(System.getenv("rememberMe_secret"), userDetailsService);
    System.out.println("rememberme 서비스 동작");
    rememberMeServices.setAlwaysRemember(false
    );
    // Remember Me 서비스의 설정 작업을 수행한다.
    return rememberMeServices;
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new Http403ForbiddenEntryPoint();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, RestTemplate restTemplate, ObjectMapper objectMapper, AccountApiServerProperties accountApiServerProperties) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService(restTemplate, objectMapper, accountApiServerProperties));
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return authenticationProvider;
  }

  @Bean
  @Primary
  @Autowired
  AuthenticationSuccessHandler successHandler(RedisTemplate<String, Object> redisTemplate) {
    return new AuthenticationSuccessHandler() {
      final RedisTemplate<String, Object> template = redisTemplate;

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
  public UserDetailsService userDetailsService(RestTemplate restTemplate, ObjectMapper objectMapper, AccountApiServerProperties accountApiServerProperties) {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username {}", username);
//        ResponseEntity<String> response =
//            RestTemplateUtil.createQuery
//                (restTemplate, accountApiServerProperties.getUrl(), accountApiServerProperties.getPort(), "/accounts", HttpMethod.GET, String.class, Map.of("id", username));
        System.out.println(accountApiServerProperties.getFullUrl() + "/accounts?id={}");
        JsonNode result = restTemplate.getForEntity(accountApiServerProperties.getFullUrl() + "/accounts/{id}", JsonNode.class, Map.of("id", username)).getBody();
        String pwd = "";
        String id = "";
        try {
          pwd = result.get("pwd").asText();
          System.out.println(pwd);
          id = result.get("id").asText();
          System.out.println(id);

          return User.builder().username(id).password(pwd).authorities(new SimpleGrantedAuthority("ROLE_MEMBER")).build();
        } catch (Exception e) {
          e.printStackTrace();
          return null;//Todo : custom exception 던지기
        }
      }
    };
  }

  class AccessDeniedExceptionHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException ex) throws IOException, ServletException {
      response.setStatus(403);
    }
  }

}
