package com.nhnacademy.minidooray.account.command;

import com.nhnacademy.minidooray.account.domain.AccountStatus;
import com.nhnacademy.minidooray.account.domain.SystemAuth;
import com.nhnacademy.minidooray.account.domain.Member;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {
    // custom annotation
    @NotNull @NotEmpty
    String id;

    @NotNull @NotEmpty
    String email;

    @NotNull @NotEmpty
    String pwd;

    @NotNull @NotEmpty
    String nickname;

    @NotNull @NotEmpty
    AccountStatus accountStatus;

    @NotNull @NotEmpty
    SystemAuth systemAuth;

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
