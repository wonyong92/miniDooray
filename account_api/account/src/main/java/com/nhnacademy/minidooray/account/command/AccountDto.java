package com.nhnacademy.minidooray.account.command;

import com.nhnacademy.minidooray.account.domain.AccountStatus;
import com.nhnacademy.minidooray.account.domain.SystemAuth;
import com.nhnacademy.minidooray.account.domain.Member;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {
    // custom annotation 필요성
    @NotNull @NotEmpty
    private String id;

    @NotNull @NotEmpty
    private String email;

    @NotNull @NotEmpty
    private String pwd;

    @NotNull @NotEmpty
    private String nickname;

    @NotNull @NotEmpty
    private AccountStatus accountStatus;

    @NotNull @NotEmpty
    private SystemAuth systemAuth;

    public Member createMember() {
        Member member = Member.builder()
            .id(id)
            .email(email)
            .pwd(pwd)
            .nickname(pwd)
            .accountStatus(accountStatus)
            .systemAuth(systemAuth)
            .build();

        return member;
    }
}
