package com.nhnacademy.minidooray.account.controller;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.repository.MemberRepository;
import com.nhnacademy.minidooray.account.service.MemberService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Slf4j
public class AccountController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping
    public ResponseEntity<Member> getMember(HttpServletRequest request, @RequestParam String id,
        @RequestHeader(name = "clientId", required = false) String clientId) {
        if (
            System.getenv("client_secret") != null && clientId != null &&
                clientId.equals(System.getenv("client_secret"))) {
            Optional<Member> member = memberRepository.findById(id);
            if (member.isPresent()) {
                return ResponseEntity.ok(member.get());
            } else {
                throw new NoSuchElementException("Member not found");
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
    @GetMapping("/{memberId}")
    public String view(@PathVariable String memberId, Model model) {
        Member member = memberService.getMember(memberId);
        model.addAttribute("member", member);
        return "member";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Member> members = memberService.getMembers();
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/{memberId}/edit")
    public String editMember(@PathVariable String memberId, Model model) {
        Member member = memberService.getMember(memberId);
        model.addAttribute(member);
        return "editForm";
    }

    @PostMapping("/{memberId}/edit")
    public String editMember(@PathVariable String memberId, @ModelAttribute AccountDto accountDto) {
        Member member = accountDto.createMember();
        memberService.updateMember(memberId, member);
        return "redirect:/members/{memberId}";
    }

    @PostMapping("/{memberId}")
    public String delete(@PathVariable String memberId) {
        memberService.deleteMember(memberId);
        return "redirect:/members";
    }
}
