package com.nhnacademy.minidooray.account.service;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.AccountStatus;
import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.ex.MemberDuplicatedException;
import com.nhnacademy.minidooray.account.ex.MemberNotFoundException;
import com.nhnacademy.minidooray.account.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class  DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public String createMember(AccountDto accountDto) {
        String memberId = accountDto.getId();

        if (memberRepository.existsById(memberId)) {
            throw new MemberDuplicatedException("Member already exists!");
        }

        Member member = Member.builder()
            .id(memberId)
            .pwd(accountDto.getPwd())
            .email(accountDto.getEmail())
            .nickname(accountDto.getNickname())
            .accountStatus(accountDto.getAccountStatus())
            .systemAuth(accountDto.getSystemAuth())
            .build();

        return memberRepository.save(member).getId();
    }

    @Override
    public Member getMember(String memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        if (findMember.isPresent()) {
            return findMember.get();
        }
        throw new MemberNotFoundException("Member Not Found!");
    }

    @Override
    public List<AccountDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<AccountDto> accountDtos = new ArrayList<>();

        for (Member member : members) {
            AccountDto accountDto = new AccountDto();
            accountDto.setId(member.getId());
            accountDto.setEmail(member.getEmail());
            accountDto.setPwd(member.getPwd());
            accountDto.setNickname(member.getNickname());
            accountDto.setAccountStatus(member.getAccountStatus());
            accountDto.setSystemAuth(member.getSystemAuth());

            accountDtos.add(accountDto);
        }

        return accountDtos;
    }

    @Override
    @Transactional
    public void updateMember(String memberId, AccountDto updateParam) {
        Member member = this.getMember(memberId);

        member.setPwd(updateParam.getPwd());
        member.setEmail(updateParam.getEmail());
        member.setNickname(updateParam.getNickname());
        member.setAccountStatus(updateParam.getAccountStatus());
        member.setSystemAuth(updateParam.getSystemAuth());

        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void deleteMember(String memberId) {
        Member member = this.getMember(memberId);
        member.setAccountStatus(AccountStatus.WITHDRAWN);
    }

}
