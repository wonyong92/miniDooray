package com.nhn.academy.minidooray.gateway.controller.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.domain.account.AccountDto;
import com.nhn.academy.minidooray.gateway.domain.account.AccountUpdateDto;
import com.nhn.academy.minidooray.gateway.service.account.AccountService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller

public class AccountController {

  final RestTemplate template;
  final AccountApiServerProperties accountApiServerProperties;
  @Autowired
  public AccountController(RestTemplate template, AccountApiServerProperties accountApiServerProperties) {
    this.template = template;
    this.accountApiServerProperties = accountApiServerProperties;

    accountUrl = accountApiServerProperties.getUrl();
    taskUrl = accountApiServerProperties.getUrl();
    accountPort= accountApiServerProperties.getPort();
    taskPort= accountApiServerProperties.getPort();
  }

  String accountUrl ;


  String taskUrl ;

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


  @GetMapping("/account")
  public ResponseEntity<String> getMemberData(@RequestParam(name = "id") String id) throws JsonProcessingException {
    String result = accountService.getAccount(id);

    return ResponseEntity.ok()
        .header("Custom-Header", "Value")
        .body(result);

  }

  @Autowired
  AccountApiServerProperties properties;

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

  @PostMapping("/account/update")
  public ResponseEntity<String> updateMemberData(@RequestBody AccountUpdateDto dto, @RequestParam(name="id") String id)
  {
    //비밀번호 변경 = Oauth 회원의 경우 비밀번호 정보 없음 - 변경 기능 비활성화
    //닉네임 변경 정도만 구현하기
    //dto에서 null이 아닌 필드 확인
    //프론트에서 회원 정보를 포함하여 form 수행으로 가정 - 기존 데이터와 변경점을 확인 하고 update 수행?, 그냥 update 수행?
    //account에서 결정?
    //현재 로그인 유저의 정보 변경인지 확인 필요
    System.out.println("현재 로그인 유저 : "+SecurityContextHolder.getContext().getAuthentication().getName()
        +"\n"+"변경 대상 유저 :"+id
        +"\n" + dto.toString()
    );
    return new ResponseEntity<>(HttpStatus.OK);
  }


}

