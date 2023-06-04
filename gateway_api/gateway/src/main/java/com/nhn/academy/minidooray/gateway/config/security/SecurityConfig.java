package com.nhn.academy.minidooray.gateway.config.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.properties.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.domain.gateway.UserDetail;
import com.nhn.academy.minidooray.gateway.security.details.service.CustomOAuth2MemberService;
import com.nhn.academy.minidooray.gateway.security.handler.OauthLoginSuccessHandler;
import com.nhn.academy.minidooray.gateway.util.RestTemplateUtil;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity(debug = true)
@Slf4j
public class SecurityConfig {

  @Autowired
  AuthenticationSuccessHandler successHandler;

  @Autowired
  OauthLoginSuccessHandler oauthLoginSuccessHandler;

  @Autowired
  CustomOAuth2MemberService oAuth2MemberService;
//  @Autowired
//  UserDetailsService userDetailsService;

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  ObjectMapper objectMapper;

  @Bean
  public OidcUserService oidcUserService() {
    return new OidcUserService();
  }

  //기본 필터 순서 - csrf - oauth - formlogin
  @Bean
  protected SecurityFilterChain configTest(HttpSecurity http) throws Exception {
//    http
//        .csrf().disable()
//        .anonymous().disable()
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
//        .formLogin()
//        .loginProcessingUrl("/login")
//        .successHandler(successHandler)
//        .and()
//        .logout()
//        .invalidateHttpSession(true)
//        .logoutUrl("/logout")
//        .deleteCookies("JSESSIONID")
//        .deleteCookies("SESSIONID")
//        .deleteCookies("SESSION")
//        .deleteCookies("ACCESS_TOKEN")
//        .deleteCookies("REFRESH_TOKEN")
//        .logoutSuccessUrl("/login")
//        .and()
//        .oauth2Login()
//        /*oauth 로그인 수행 지점 설정*/
//        .authorizationEndpoint()
//        .baseUri("/login")
//        .and()
//        .successHandler(oauthLoginSuccessHandler)
//        //.defaultSuccessUrl("/") //successHandler 를 덮어쓴다
//        .userInfoEndpoint()
//        .userService(oAuth2MemberService)
//        .and().and().authenticationProvider(provider)
//        .authorizeRequests()
//        .antMatchers("/login/*")
//        .permitAll()
//        .anyRequest().authenticated().and()
//        .rememberMe().rememberMeParameter(System.getenv("rememberMe_secret")).userDetailsService(userDetailsService)
//    ;

    http.csrf().disable();
    http.anonymous().disable();
    //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//공부 필요
    http.rememberMe().key(System.getenv("rememberMe_secret")).rememberMeParameter("rememberMe").userDetailsService(userDetailsService());
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
        //.defaultSuccessUrl("/") //successHandler 를 덮어쓴다
        .userInfoEndpoint()
        .userService(oAuth2MemberService);

    http.authenticationProvider(authenticationProvider())
        .authorizeRequests()
        .antMatchers("/login/*")
        .permitAll()
        .anyRequest().authenticated();

    //remember me 사용시 로그인 성공 후 바로 리다이렉트가 아닌 토큰 로그인을 다시 수행하여 최종적으로 인증을 마친다

    return http.build();
  }


  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new Http403ForbiddenEntryPoint();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return authenticationProvider;
  }

  @Autowired
  RedisTemplate<String, Object> redisTemplate;

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

  @Autowired
  AccountApiServerProperties accountApiServerProperties;


  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<String> response = RestTemplateUtil.createQuery(restTemplate, accountApiServerProperties.getUrl(), accountApiServerProperties.getPort(), "/accounts", HttpMethod.GET, String.class, Map.of("id", username));

        String pwd;
        String id;
        try {
          UserDetail userDetail = objectMapper.readValue(response.getBody(), UserDetail.class);
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

}
