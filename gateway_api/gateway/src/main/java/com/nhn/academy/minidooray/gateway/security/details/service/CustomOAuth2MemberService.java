package com.nhn.academy.minidooray.gateway.security.details.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>  {
  /*
  *
  * OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) 메서드는
  * 서드파티에 사용자 정보를 요청할 수 있는 access token 을 얻고나서 실행
  * */
  private final RestTemplate restTemplate;
  private final HttpSession session;
  private final ObjectMapper mapper;

  private final RedisTemplate<String,Object> redisTemplate;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    DefaultOAuth2UserService service = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = service.loadUser(userRequest);

    String provider = userRequest.getClientRegistration().getRegistrationId();

    if (provider.contains("github")) {
      System.out.println("github 로그인 수행");

      String accessToken = userRequest.getAccessToken().getTokenValue();
      session.setAttribute("oAuthToken", accessToken);

      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "token " + accessToken);

      ResponseEntity<String> response = restTemplate.exchange(
          "https://api.github.com/user/emails",  // 요청 URL
          HttpMethod.GET,                     // HTTP 메서드
          new HttpEntity<>(headers),            // 요청 본문 데이터 + 헤더
          String.class                        // 응답 데이터 타입
      );

      String privateEmail = "";
      String publicEmail = "";
      Map<String, Object> attributes;
      try {
        List<Map<String, Object>> result = mapper.readValue(response.getBody(), new TypeReference<>() {
        });
        //System.out.println(result);
        privateEmail = result.get(1).get("email").toString();//private email 읽기
        publicEmail = result.get(0).get("email").toString();//public email 읽기

        attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("public_email", publicEmail);
        attributes.put("private_email", privateEmail);
        System.out.println("oauth 로그인 중~~~~ : " + userRequest.getAdditionalParameters());
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }

      return new DefaultOAuth2User(
          Collections.singleton(new SimpleGrantedAuthority("user")),
          attributes, "private_email");
      //nameAttributeKey 의 벨류 = Principal name 에 저장
    }
    return null;
  }
}
