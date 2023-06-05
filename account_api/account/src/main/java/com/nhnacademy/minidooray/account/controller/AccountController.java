package com.nhnacademy.minidooray.account.controller;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.AccountStatusEnum;
import com.nhnacademy.minidooray.account.domain.GatewayAuthEnum;
import com.nhnacademy.minidooray.account.domain.IsRegisteredEnum;
import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.ex.MemberNotFoundException;
import com.nhnacademy.minidooray.account.service.MemberService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Slf4j
public class AccountController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(HttpServletRequest request, @PathVariable("memberId") String memberId,
        @RequestHeader(name = "clientId", required = false) String clientId) {
        try {
            if (System.getenv("client_secret") != null && clientId != null &&
                clientId.equals(System.getenv("client_secret"))) {

                Member member = memberService.getMember(memberId);

                if (member != null) {
                    return ResponseEntity.ok(member);
                } else {
                    throw new MemberNotFoundException("Member not found");
                }
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (MemberNotFoundException e) {
            // 다른 예외는 서버 오류로 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody AccountDto accountDto,
        HttpServletRequest request) {

        log.info("member id={}", accountDto.getId());

        memberService.createMember(accountDto);
        return ResponseEntity.ok().build();
    }

    // ----------------------------------------

    /**
     * 테스트용 메소드 작성
     */
    @ResponseBody
    @GetMapping("/api")
    public List<Member> list() {
        List<Member> members = memberService.getMembers();
        return members;
    }

    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }

    /**
     * Extra method: createMember()
     */
    @PostMapping("/create")
    public String create(AccountDto accountDto) {
        memberService.createMember(accountDto);
        return "redirect:/members";
    }

    /**
     * Extra method: getMember()
     */
/*    @GetMapping("/{memberId}")
    public String view(@PathVariable String memberId, Model model) {
        Member member = memberService.getMember(memberId);
        model.addAttribute("member", member);
        return "member";
    }*/

    @GetMapping("/list")
    public List<Member> list(Model model) {
        List<Member> members = memberService.getMembers();
        model.addAttribute("members", members);
        return members;
    }

    @GetMapping("/{memberId}/edit")
    public String editMember(@PathVariable String memberId, Model model) {
        Member member = memberService.getMember(memberId);
        model.addAttribute(member);
        return "editForm";
    }

    @PostMapping("/{memberId}/edit")
    public Member editMember(@PathVariable String memberId, @ModelAttribute AccountDto accountDto) {
        Member member = accountDto.createMember();
        memberService.updateMember(memberId, member);
        return member;
    }

//    @PostMapping("/{memberId}")
//    public void delete(@PathVariable String memberId) {
//        memberService.deleteMember(memberId);
//        return s;
//    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        memberService.createMember(
            new AccountDto("nhnacademy_test", "nhnacademy_test@gmail.com", "1234", "nhn_test",
                AccountStatusEnum.REGISTERED, GatewayAuthEnum.ADMIN,
                IsRegisteredEnum.HAS_PERMISSION));
        memberService.createMember(
            new AccountDto("kusun1020_test", "kusun1020_test@gmail.com", "0000", "ngs_test",
                AccountStatusEnum.DORMANT, GatewayAuthEnum.USER, IsRegisteredEnum.NO_PERMISSION));
    }
}
