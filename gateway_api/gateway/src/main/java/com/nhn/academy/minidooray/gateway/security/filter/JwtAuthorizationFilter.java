package com.nhn.academy.minidooray.gateway.security.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.service.account.AccountService;
import com.nhn.academy.minidooray.gateway.util.CookieUtils;
import java.io.IOException;
import java.lang.reflect.Member;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

  private final AccountService accountService;
  private final ObjectMapper objectMapper;


  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    System.out.println("jwt 필터 진입");
    String access_token = CookieUtils.getCookieValue((HttpServletRequest)request,"ACCESS_TOKEN");

    String refresh_token = ((HttpServletRequest)request).getHeader("REFRESH_TOKEN");

    if (access_token == null || !access_token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
    }//bearer 부분 자르기
    String token = access_token.replace(JwtProperties.TOKEN_PREFIX, "");

    String username = null;
    String restoreAccessToken = null;

    try {
      username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
          .getClaim("id").asString();
      System.out.println("jwt username : "+username);

      if (username != null) {
        System.out.println("dddddddddddddddd");
        String userData = accountService.getAccount(username);
        JsonNode node = objectMapper.readTree(userData);
        System.out.println("nodeId : "+node.get("id"));

        // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
        // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
//        PrincipalDetails principalDetails = new PrincipalDetails(member);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
//            null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
//            principalDetails.getAuthorities());

        // 강제로 시큐리티의 세션에 접근하여 값 저장
//        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

      chain.doFilter(request, response);

    } catch (TokenExpiredException e) {
      //만료된 액세스 토큰 처리

      }
    chain.doFilter(request, response);
    }

}
