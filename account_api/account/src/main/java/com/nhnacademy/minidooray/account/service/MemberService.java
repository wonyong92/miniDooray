package com.nhnacademy.minidooray.account.service;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.Member;
import java.util.List;

public interface MemberService {

    String createMember(AccountDto accountDto);

    Member getMember(String memberId);

    List<AccountDto> getAllMembers();

    void updateMember(String memberId, AccountDto accountDto);

    void deleteMember(String memberId);
}
