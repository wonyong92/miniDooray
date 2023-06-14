package com.nhnacademy.minidooray.account.domain;

import com.nhnacademy.minidooray.account.command.AccountDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "member")
public class Member {

    @Id
    String id;
    @Column(unique = true)
    String email;
    String pwd;
    String nickname;
    AccountStatus accountStatus;
    SystemAuth systemAuth;

    public void update(AccountDto accountDto) {
        this.pwd = accountDto.getPwd();
        this.email = accountDto.getEmail();
        this.nickname = accountDto.getNickname();
        this.accountStatus = accountDto.getAccountStatus();
        this.systemAuth = accountDto.getSystemAuth();
    }

    public void delete() {
        this.accountStatus = AccountStatus.WITHDRAWN;
    }

}
