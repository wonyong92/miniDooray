package com.nhnacademy.minidooray.account.controller;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.AccountStatus;
import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.domain.SystemAuth;
import com.nhnacademy.minidooray.account.service.MemberService;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * C: 회원가입 - 기본적인 회원가입 (validation) R: 회원정보 조회 - 간단 조회 (memberId, nickname, email), 로그인 데이터 조회
 * (memberId, pwd) or (email, pwd) U: 회원정보 수정 - 유저정보 전체를 받아서 바뀐것만 update D: 회원 탈퇴 - status만 변경
 * <p>
 * 응답 - 회원가입: created code - 회원정보 중복 확인: memberId or email - 회원정보 간단 조회: memberId, nickname, email
 * (JSON) - 회원정보 로그인 조회: memberId or email, pwd (JSON) - 회원정보 수정: email, nickname, password ...
 * (JSON) - 회원탈퇴: ok
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Slf4j
public class AccountController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody @Valid AccountDto accountDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("validation error");
        }
        memberService.createMember(accountDto);

        // create call check
        log.info("call AccountController.createMember");
        log.info("member id={}", accountDto.getId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 회원정보 조회
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@PathVariable("memberId") String memberId, @RequestHeader(name = "clientId", required = false) String clientId) {
        if (System.getenv("client_secret") != null && clientId != null && clientId.equals(System.getenv("client_secret"))) {
            Member member = memberService.getMember(memberId);

            // get call check
            log.info("call AccountController.getMember");
            log.info("member id={}", member.getId());

            return ResponseEntity.ok(member);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * 회원정보 수정
     */
    @PostMapping("/{memberId}/update")
    public ResponseEntity<Member> updateMember(@PathVariable String memberId, @RequestBody AccountDto accountDto) {
        memberService.updateMember(memberId, accountDto);

        // update call check
        log.info("call AccountController.updateMember");
        log.info("member id={}", accountDto.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 회원 탈퇴
     */
    @PostMapping("/{memberId}")
    public ResponseEntity<Member> deleteMember(@PathVariable String memberId) {
        memberService.deleteMember(memberId);

        // delete call check
        log.info("call AccountController.deleteMember");
        log.info("member id={}", memberId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 테스트용 데이터 추가 -> DB Tool 통해서 데이터 관리 필요
     */
//    @PostConstruct
//    public void init() {
//        memberService.createMember(
//            new AccountDto("nhnacademy_test", "nhnacademy_test@gmail.com", "12345678", "nhn_test",
//                AccountStatus.REGISTERED, SystemAuth.ADMIN));
//        memberService.createMember(
//            new AccountDto("kusun1020_test", "kusun1020_test@gmail.com", "00000000", "ngs_test",
//                AccountStatus.DORMANT, SystemAuth.USER));
//    }
}
