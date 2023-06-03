package com.nhn.academy.minidooray.gateway.security;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
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

    super.onAuthenticationSuccess(request, response, authentication);
  }

}
