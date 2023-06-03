package com.nhn.academy.minidooray.gateway;


import com.nhn.academy.minidooray.gateway.security.CustomOAuth2MemberService;
import com.nhn.academy.minidooray.gateway.security.OauthLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig {
  @Autowired
  AuthenticationSuccessHandler successHandler;
@Autowired
  AuthenticationProvider provider;
  @Autowired
  OauthLoginSuccessHandler oauthLoginSuccessHandler;

  @Autowired
  CustomOAuth2MemberService oAuth2MemberService;

  @Bean
  public OidcUserService oidcUserService() {
    return new OidcUserService();
  }
  //기본 필터 순서 - csrf - oauth - formlogin
  @Bean
  @Profile(value = "test")
  protected SecurityFilterChain configTest(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests().anyRequest().permitAll().and()
        .csrf().disable()
        .oauth2Login()
        /*oauth 로그인 수행 지점 설정*/
        .authorizationEndpoint()
        .baseUri("/login").and()
        .successHandler(oauthLoginSuccessHandler)
        //.defaultSuccessUrl("/") //successHandler 를 덮어쓴다
        .userInfoEndpoint()
        .userService(oAuth2MemberService)
        .and().and()
        .formLogin()
        .successHandler(successHandler)
        .and().authenticationProvider(provider)

    ;


    return http.build();
  }

  @Bean
  @Profile(value = "dev")
  protected SecurityFilterChain config(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests().anyRequest().permitAll().and()
        .csrf().disable()
        .oauth2Login()
        /*oauth 로그인 수행 지점 설정*/
        .authorizationEndpoint()
        .baseUri("/login").and()
        .successHandler(oauthLoginSuccessHandler)
        //.defaultSuccessUrl("/") //successHandler 를 덮어쓴다
        .userInfoEndpoint()
        .userService(oAuth2MemberService)
        .and().and()
        .formLogin()
        .successHandler(successHandler)
        .and().authenticationProvider(provider)

    ;


    return http.build();
  }


}
