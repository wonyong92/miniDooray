package com.nhn.academy.minidooray.gateway.security;

import static com.nhn.academy.minidooray.gateway.security.filter.JwtProperties.TOKEN_PREFIX;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nhn.academy.minidooray.gateway.security.filter.JwtProperties;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication)
      throws IOException, ServletException {
    System.out.println("oauth2 login success");
    HttpSession session = request.getSession(false);
    String sessionId = session.getId();

    DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();

    String username = userDetails.getName();
    String authority = new ArrayList<>(userDetails.getAuthorities()).get(0).getAuthority();

    redisTemplate.opsForHash().put(sessionId, "username", username);
    redisTemplate.opsForHash().put(sessionId, "authority", authority);
    redisTemplate.opsForHash().put(sessionId, "Attribute", ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes());
    //로그인 완료 후 Oauth2MemberService 수행 후 수행되는 내용
    //Todo : ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes() 를 활용하여 회원 가입 진행
    //    JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(session.getAttribute("Attribute")));
    //    System.out.println(jsonNode.get("private_email"));
    //

    String accessToken = JWT.create().withClaim("id",userDetails.getName()).sign(Algorithm.HMAC512(JwtProperties.SECRET));


    Cookie accessJwt = new Cookie("ACCESS_TOKEN", TOKEN_PREFIX+accessToken);
    accessJwt.setDomain("localhost");
    accessJwt.setPath("/");

    Cookie refreshJwt = new Cookie("REFRESH_TOKEN","testrefreshtoken");
    refreshJwt.setDomain("localhost");
    refreshJwt.setPath("/");
    refreshJwt.setHttpOnly(true);
    response.addCookie(accessJwt);
    response.addCookie(refreshJwt);
    session.setAttribute("Attribute",((DefaultOAuth2User) authentication.getPrincipal()).getAttributes());

    Cookie cookie = new Cookie("SESSION", sessionId);
    cookie.setMaxAge(259200);     // 3일
    cookie.setDomain("localhost");
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    response.addCookie(cookie);

    super.onAuthenticationSuccess(request, response, authentication);
  }

}
