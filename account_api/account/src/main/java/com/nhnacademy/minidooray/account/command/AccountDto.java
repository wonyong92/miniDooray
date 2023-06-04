package com.nhnacademy.minidooray.account.command;

import com.nhnacademy.minidooray.account.domain.AccountStatusEnum;
import com.nhnacademy.minidooray.account.domain.GatewayAuthEnum;
import com.nhnacademy.minidooray.account.domain.IsRegisteredEnum;
import com.nhnacademy.minidooray.account.domain.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AccountDto {

    String id;
    String email;
    String pwd;
    String username;
    AccountStatusEnum accountStatusEnum;
    GatewayAuthEnum gatewayAuthEnum;
    IsRegisteredEnum isRegisteredEnum;

    public Member createMember() {
        Member member = Member.builder().id(id)
            .pwd(pwd)
            .email(email)
            .username(username)
            .accountStatusEnum(accountStatusEnum)
            .gatewayAuthEnum(gatewayAuthEnum)
            .isRegisteredEnum(isRegisteredEnum)
            .build();

        return member;
    }
}
