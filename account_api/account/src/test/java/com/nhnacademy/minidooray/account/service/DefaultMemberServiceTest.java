package com.nhnacademy.minidooray.account.service;

import com.nhnacademy.minidooray.account.command.AccountDto;
import com.nhnacademy.minidooray.account.domain.AccountStatus;
import com.nhnacademy.minidooray.account.domain.SystemAuth;
import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.ex.MemberDuplicatedException;
import com.nhnacademy.minidooray.account.ex.MemberNotFoundException;
import com.nhnacademy.minidooray.account.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DefaultMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private DefaultMemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createMember_ValidAccountDto_ReturnsMemberId() {
        // Given
        AccountDto accountDto = new AccountDto("testId", "testEmail", "testPwd", "testNickname",
            AccountStatus.REGISTERED, SystemAuth.USER);

        when(memberRepository.existsById(anyString())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(new Member("testId", "testEmail",
            "testPwd", "testNickname", AccountStatus.REGISTERED, SystemAuth.USER));

        // When
        String memberId = memberService.createMember(accountDto);

        // Then
        assertEquals("testId", memberId);
    }

    @Test
    void createMember_DuplicatedAccountId_ThrowsMemberDuplicatedException() {
        // Given
        AccountDto accountDto = new AccountDto("testId", "testEmail", "testPwd", "testNickname",
            AccountStatus.REGISTERED, SystemAuth.USER);

        when(memberRepository.existsById(anyString())).thenReturn(true);

        // Then
        assertThrows(MemberDuplicatedException.class, () -> memberService.createMember(accountDto));
    }

    @Test
    void getMember_ExistingMemberId_ReturnsMember() {
        // Given
        String memberId = "testId";
        Member expectedMember = new Member("testId", "testEmail", "testPwd", "testNickname",
            AccountStatus.REGISTERED, SystemAuth.USER);

        when(memberRepository.findById(anyString())).thenReturn(Optional.of(expectedMember));

        // When
        Member member = memberService.getMember(memberId);

        // Then
        assertEquals(expectedMember, member);
    }

    @Test
    void getMember_NonExistingMemberId_ThrowsMemberNotFoundException() {
        // Given
        String memberId = "nonExistingId";

        when(memberRepository.findById(anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(MemberNotFoundException.class, () -> memberService.getMember(memberId));
    }

    @Test
    void updateMember_ExistingMemberId_UpdateMember() {
        // Given
        String memberId = "testId";
        Member existingMember = new Member("testId", "testEmail", "testPwd", "testNickname",
            AccountStatus.REGISTERED, SystemAuth.USER);
        AccountDto updateParam = new AccountDto("testId", "updatedEmail", "updatedPwd", "updatedNickname",
            AccountStatus.DORMANT, SystemAuth.ADMIN);

        when(memberRepository.findById(eq(memberId))).thenReturn(Optional.of(existingMember));

        // When
        memberService.updateMember(memberId, updateParam);

        // Then
        verify(memberRepository, times(1)).save(existingMember);
        assertEquals(updateParam.getEmail(), existingMember.getEmail());
        assertEquals(updateParam.getPwd(), existingMember.getPwd());
        assertEquals(updateParam.getNickname(), existingMember.getNickname());
        assertEquals(updateParam.getAccountStatus(), existingMember.getAccountStatus());
        assertEquals(updateParam.getSystemAuth(), existingMember.getSystemAuth());
    }

    @Test
    void updateMember_NonExistingMemberId_ThrowsMemberNotFoundException() {
        // Given
        String memberId = "nonExistingId";
        AccountDto updateParam = new AccountDto("testId", "updatedEmail", "updatedPwd", "updatedNickname",
            AccountStatus.DORMANT, SystemAuth.ADMIN);

        when(memberRepository.findById(eq(memberId))).thenReturn(Optional.empty());

        // Then
        assertThrows(MemberNotFoundException.class, () -> memberService.updateMember(memberId, updateParam));
    }

    @Test
    void deleteMember_ExistingMemberId_SetAccountStatusAsWithdrawn() {
        // Given
        String memberId = "testId";
        Member existingMember = new Member("testId", "testEmail", "testPwd", "testNickname",
            AccountStatus.REGISTERED, SystemAuth.USER);

        memberRepository.save(existingMember);

        when(memberRepository.findById(eq(memberId))).thenReturn(Optional.of(existingMember));

        // When
        memberService.deleteMember(memberId);

        // Then
        verify(memberRepository, times(1)).save(existingMember);
        assertEquals(AccountStatus.WITHDRAWN, existingMember.getAccountStatus());
    }

    @Test
    void deleteMember_NonExistingMemberId_ThrowsMemberNotFoundException() {
        // Given
        String memberId = "nonExistingId";

        when(memberRepository.findById(eq(memberId))).thenReturn(Optional.empty());

        // Then
        assertThrows(MemberNotFoundException.class, () -> memberService.deleteMember(memberId));
    }
}
