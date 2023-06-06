package com.nhnacademy.minidooray.account.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.repository.MemberRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AccountController {
  private final MemberRepository memberRepository;
  @GetMapping("/accounts")
  public ResponseEntity<Member> getMember(HttpServletRequest request, @RequestParam(name="id") String id, @RequestHeader(name="clientId",required = false) String clientId){
    if(
//        System.getenv("client_secret")!=null && clientId !=null &&
//        clientId.equals(System.getenv("client_secret"))
    true)
      return ResponseEntity.ok(memberRepository.findById(id).orElse(null));
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PostMapping("/accounts")
  public ResponseEntity<Member> createMember(@RequestBody Member member, HttpServletRequest request){

    System.out.println(member);
    return ResponseEntity.ok(memberRepository.save(member));
  }

  @GetMapping("/accounts/all")
  public ResponseEntity<List<Member>> getMembers(HttpServletRequest request, @RequestHeader(name="clientId",required = false) String clientId){

      return ResponseEntity.ok(memberRepository.findAll());

  }
  @GetMapping("/projects/members")
  public ResponseEntity<List<Member>> getPrMembers(HttpServletRequest request, String projectId){
    for(Object t : request.getParameterMap().keySet()){
      System.out.println(t);
    }
    return ResponseEntity.ok(memberRepository.findAll());
  }

  @PostMapping("/accounts/exist/id")
  public Boolean checkExistId(@RequestBody String clientId){
    log.info("client Id : {}",clientId);
    return true;
  }
  @Autowired
  ObjectMapper objectMapper;

  @PostMapping("/accounts/exist/email")
  public Boolean checkExistEmail(@RequestBody JsonNode clientEmail) throws JsonProcessingException {
    JsonNode email = objectMapper.readTree("{\"clientEmail\":\"test@naver.com\",\"test\":\"test\"}");
    log.info("client email : {}",email.get("clientEmail"));
    log.info("client email : {}",email.get(0));
    log.info("client email : {}",clientEmail.asText());

    log.info("client email : {}",objectMapper.readTree(clientEmail.get("clientEmail").asText()).get("clientEmail"));
    log.info("client email : {}","{\"clientEmail\":\"test@naver.com\",\"test\":\"test\"}");


    return true;
  }

}

