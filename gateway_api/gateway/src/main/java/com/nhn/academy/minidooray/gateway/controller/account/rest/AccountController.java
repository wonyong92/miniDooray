package com.nhn.academy.minidooray.gateway.controller.account.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.properties.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.domain.account.AccountDto;
import com.nhn.academy.minidooray.gateway.domain.account.AccountUpdateDto;
import com.nhn.academy.minidooray.gateway.service.account.service.AccountService;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class AccountController {

  final RestTemplate template;
  final AccountApiServerProperties accountApiServerProperties;
  String accountUrl;
  String taskUrl;
  String accountPort;
  String taskPort;
  @Autowired
  ObjectMapper mapper;
  @Autowired
  PasswordEncoder encoder;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  AccountService accountService;

  @Autowired
  RedisTemplate<String, Object> redisTemplate;
  @Autowired
  AccountApiServerProperties properties;

  @Autowired
  public AccountController(RestTemplate template, AccountApiServerProperties accountApiServerProperties) {
    this.template = template;
    this.accountApiServerProperties = accountApiServerProperties;

    accountUrl = accountApiServerProperties.getUrl();
    taskUrl = accountApiServerProperties.getUrl();
    accountPort = accountApiServerProperties.getPort();
    taskPort = accountApiServerProperties.getPort();
  }

  @GetMapping("/account/{memberId}")
  public ResponseEntity<String> getMemberData(HttpServletRequest request, @PathVariable String memberId) throws JsonProcessingException {
    String result = accountService.getAccount(memberId);
    HttpSession session = request.getSession(false);//서블릿 세션 가져오기
    log.info("session 상태 : " + session.getId());
    redisTemplate.opsForHash().put(session.getId(), "testValue", "test");
    log.info("redis session : " + redisTemplate.opsForHash().get(session.getId(), "testValue"));

    return ResponseEntity.ok()
        .header("Custom-Header", "Value")
        .body(result);
  }

  @GetMapping("/accounts")
  public ResponseEntity<String> getAllMemberData() {
    return ResponseEntity.ok().body(accountService.getAllAccounts());
  }

  @GetMapping("/account/id/{memberId}")
  public ResponseEntity<String> getMemberDataById(@PathVariable String memberId) {
    return ResponseEntity.ok().body(accountService.getAccountById(memberId));
  }

  @GetMapping("/account/password/{memberId}")
  public ResponseEntity<String> getMemberPwdById(@PathVariable String memberId) {
    return ResponseEntity.ok().body(accountService.getPwdById(memberId));
  }

  @GetMapping("/account/email/{memberId}")
  public ResponseEntity<String> getMemberEmailById(@PathVariable String memberId) {
    return ResponseEntity.ok().body(accountService.getEmailById(memberId));
  }

  @PutMapping("/account/{memberId}")
  public ResponseEntity<String> updateMemberData(@RequestBody AccountDto dto, @PathVariable String memberId) {
    accountService.putAccount(dto, memberId);
    return ResponseEntity.ok().build();
  }


  @PostMapping("/account")
  public ResponseEntity<String> createMemberData(@RequestBody AccountDto dto) throws JsonProcessingException {
    dto.setPwd(encoder.encode(dto.getPwd()));

    String result = accountService.createAccount(dto);

    return ResponseEntity.created(

        URI.create(
            accountUrl + accountPort + "/accounts" + "/" + dto.getId()
        )
    ).body(result);
  }


  @PostMapping("/account/update/{memberId}")
  public ResponseEntity<String> updateMemberData(@RequestBody AccountUpdateDto dto, @PathVariable(name = "id") String id) {
    //비밀번호 변경 = Oauth 회원의 경우 비밀번호 정보 없음 - 변경 기능 비활성화
    //닉네임 변경 정도만 구현하기
    //dto에서 null이 아닌 필드 확인
    //프론트에서 회원 정보를 포함하여 form 수행으로 가정 - 기존 데이터와 변경점을 확인 하고 update 수행?, 그냥 update 수행?
    //account에서 결정?
    //현재 로그인 유저의 정보 변경인지 확인 필요
    System.out.println("현재 로그인 유저 : " + SecurityContextHolder.getContext().getAuthentication().getName()
        + "\n" + "변경 대상 유저 :" + id
        + "\n" + dto.toString()
    );
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /*
   * 회원가입
   * 아이디 중복 조회
   * 이메일 중복 조회
   * 회원 간단 조회
   * 회원 자세히 조회
   * 회원 정보 수정
   * 회원 탈퇴
   *
   *
   * */

  @PostMapping("/account/exist/id")
  public ResponseEntity<Boolean> checkExitstId(@RequestBody String id) {
    return accountService.checkExistId(id);
  }

  @PostMapping("/account/exist/email")
  public ResponseEntity<Boolean> checkExitstEmail(@RequestBody String email) {
    return accountService.checkExistEmail(email);
  }

  @DeleteMapping("/account/{memberId}")
  public ResponseEntity<Void> deleteAccount(@PathVariable String memberId) {
    accountService.deleteAccount(memberId);
    return ResponseEntity.ok().build();
  }

}

