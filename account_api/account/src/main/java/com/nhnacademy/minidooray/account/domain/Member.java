package com.nhnacademy.minidooray.account.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "member")
@Builder
public class Member {

    @Id
    String id;
    String email;
    String pwd;
    String username;
    AccountStatusEnum accountStatusEnum;
    GatewayAuthEnum gatewayAuthEnum;
    IsRegisteredEnum isRegisteredEnum;

}
