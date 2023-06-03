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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
  @Autowired
  AuthenticationSuccessHandler successHandler;
@Autowired
  AuthenticationProvider provider;
  @Autowired
  OauthLoginSuccessHandler oauthLoginSuccessHandler;

  @Autowired
  CustomOAuth2MemberService oAuth2MemberService;
  @Autowired
  UserDetailsService userDetailsService;

  @Bean
  public OidcUserService oidcUserService() {
    return new OidcUserService();
  }
  //기본 필터 순서 - csrf - oauth - formlogin
  @Bean
  protected SecurityFilterChain configTest(HttpSecurity http) throws Exception {
    System.out.println("profile TEST");
    System.out.println("rememberMe_secret : "+ System.getenv("rememberMe_secret"));
    http
        .csrf().disable()
        .anonymous().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
        .formLogin()
        .loginProcessingUrl("/login")
        .successHandler(successHandler)
        .and()
        .logout()
        .invalidateHttpSession(true)
        .logoutUrl("/logout")
        .deleteCookies("JSESSIONID")
        .logoutSuccessUrl("/login")
        .and()
        .oauth2Login()
        /*oauth 로그인 수행 지점 설정*/
        .authorizationEndpoint()
        .baseUri("/login")
        .and()
        .successHandler(oauthLoginSuccessHandler)
        //.defaultSuccessUrl("/") //successHandler 를 덮어쓴다
        .userInfoEndpoint()
        .userService(oAuth2MemberService)
        .and().and().authenticationProvider(provider)
        .authorizeRequests()
        .antMatchers("/login/*")
        .permitAll()
        .anyRequest().authenticated().and()
        .rememberMe().rememberMeParameter(System.getenv("rememberMe_secret")).alwaysRemember(true).userDetailsService(userDetailsService)
    ;
   // http.addFilterBefore(jwtAuthorizationFilter, OAuth2AuthorizationRequestRedirectFilter.class);

    return http.build();
  }


  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new Http403ForbiddenEntryPoint();
  }

//  @Autowired
//  JwtAuthorizationFilter jwtAuthorizationFilter;

}
