package com.nhnacademy.minidooray.account.service;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.Member;
import java.util.List;

public interface MemberService {

    String createMember(AccountDto accountDto);

    void updateMember(String memberId, Member member);

    void deleteMember(String memberId);

    List<Member> getMembers();

    Member getMember(String memberId);
}
