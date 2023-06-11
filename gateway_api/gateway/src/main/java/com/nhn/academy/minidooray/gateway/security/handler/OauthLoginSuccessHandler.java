package com.nhn.academy.minidooray.gateway.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.domain.account.AccountDto;
import com.nhn.academy.minidooray.gateway.service.account.service.AccountService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OauthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private final RedisTemplate<String, Object> redisTemplate;
  private final AccountService accountService;
  private final ObjectMapper objectMapper;
  private final PasswordEncoder encoder;
  @Autowired
  RememberMeServices rememberMeServices;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication)
      throws IOException, ServletException {

    log.info("oauth2 login success {} ", request.getRequestURI());

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

    Map<String, Object> attribute = ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes();
    String findResult = accountService.getAccount(attribute.get("id").toString());
    log.debug("principal : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    if (findResult.equals("no_data")) {
      log.info("oauth 인증 결과 데이터로 회원 정보 조회 실패 - 회원 가입 진행");
      //설마 서로 다른 서비스에서 id 값이 겹치는 일이 있을까?
      AccountDto dto = new AccountDto();
      dto.setEmail(attribute.get("private_email").toString());
      dto.setId(attribute.get("id").toString());
      dto.setPwd(encoder.encode("teststetetset"));
      accountService.createAccount(dto);

      rememberMeServices.loginSuccess(request, response, authentication);

      //private 이메일이 변경되었음을 감지하면 변경 요청까지 보내야 할까?
    } else {
      log.info("존재하는 회원");
    }
    log.info("Attribute : " + attribute);
    session.setAttribute("id", attribute.get("id"));
    session.setAttribute("Attribute", attribute);

    //super.onAuthenticationSuccess(request, response, authentication);
  }

}
