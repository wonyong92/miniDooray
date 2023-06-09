package com.nhnacademy.minidooray.account.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
