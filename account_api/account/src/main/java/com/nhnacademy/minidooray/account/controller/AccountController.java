package com.nhnacademy.minidooray.account.controller;


import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.repository.MemberRepository;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
  private final MemberRepository memberRepository;
  @GetMapping("/accounts")
  public ResponseEntity<Member> getMember(HttpServletRequest request, @RequestParam String id, @RequestHeader(name="clientId",required = false) String clientId){
    if(
        System.getenv("client_secret")!=null && clientId !=null &&
        clientId.equals(System.getenv("client_secret")))
      return ResponseEntity.ok(memberRepository.findById(id).orElse(null));
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PostMapping("/accounts")
  public ResponseEntity<Member> createMember(@RequestBody Member member, HttpServletRequest request){

    System.out.println(member);
    return ResponseEntity.ok(memberRepository.save(member));
  }

}
