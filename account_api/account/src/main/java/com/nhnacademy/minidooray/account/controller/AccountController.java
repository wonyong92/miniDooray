package com.nhnacademy.minidooray.account.controller;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.service.MemberService;
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

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody @Valid AccountDto accountDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldError("validation error");
        }
        memberService.createMember(accountDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@PathVariable("memberId") String memberId, @RequestHeader(name = "clientId", required = false) String clientId) {
        if (System.getenv("client_secret") != null && clientId != null && clientId.equals(System.getenv("client_secret"))) {
            Member member = memberService.getMember(memberId);

            return ResponseEntity.ok(member);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

//    try {
//    } catch (MemberNotFoundException e) {
//        // 다른 예외는 서버 오류로 처리
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }

    @PostMapping("/{memberId}/update")
    public ResponseEntity<Member> updateMember(@PathVariable String memberId, @ModelAttribute AccountDto accountDto) {
        Member member = accountDto.createMember();
        memberService.updateMember(memberId, member);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{memberId}")
    public ResponseEntity<Member> delete(@PathVariable String memberId) {
        memberService.deleteMember(memberId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 테스트용 데이터 추가
     */
//    @PostConstruct
//    public void init() {
//        memberService.createMember(
//            new AccountDto("nhnacademy_test", "nhnacademy_test@gmail.com", "1234", "nhn_test",
//                AccountStatus.REGISTERED, SystemAuth.ADMIN,
//                IsRegisteredEnum.HAS_PERMISSION));
//        memberService.createMember(
//            new AccountDto("kusun1020_test", "kusun1020_test@gmail.com", "0000", "ngs_test",
//                AccountStatus.DORMANT, SystemAuth.USER, IsRegisteredEnum.NO_PERMISSION));
//    }
}
