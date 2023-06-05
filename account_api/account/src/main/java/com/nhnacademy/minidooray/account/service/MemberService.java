package com.nhnacademy.minidooray.account.service;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.Member;
import java.util.Optional;

public interface MemberService {

    String createMember(AccountDto accountDto);

    Member getMember(String memberId);

    void updateMember(String memberId, Member member);

    void deleteMember(String memberId);
}
