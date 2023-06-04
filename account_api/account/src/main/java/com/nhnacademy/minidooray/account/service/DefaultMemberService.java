package com.nhnacademy.minidooray.account.service;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.repository.MemberRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String createMember(AccountDto accountDto) {
        Member member = Member.builder().id(accountDto.getId())
            .pwd(accountDto.getPwd())
            .email(accountDto.getEmail())
            .username(accountDto.getUsername())
            .accountStatusEnum(accountDto.getAccountStatusEnum())
            .gatewayAuthEnum(accountDto.getGatewayAuthEnum())
            .isRegisteredEnum(accountDto.getIsRegisteredEnum())
            .build();

        return memberRepository.save(member).getId();
    }

    @Override
    public void updateMember(String memberId, Member updateParam) {
        Member member = this.getMember(memberId);

        member.setId(updateParam.getId());
        member.setPwd(updateParam.getPwd());
        member.setEmail(updateParam.getEmail());
        member.setUsername(updateParam.getUsername());
        member.setAccountStatusEnum(updateParam.getAccountStatusEnum());
        member.setGatewayAuthEnum(updateParam.getGatewayAuthEnum());
        member.setIsRegisteredEnum(updateParam.getIsRegisteredEnum());
    }

    @Override
    public void deleteMember(String memberId) {
        Member member = memberRepository.findById(memberId).
            orElseThrow(NoSuchElementException::new);
        memberRepository.delete(member);
    }

    @Override
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMember(String memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }
}
