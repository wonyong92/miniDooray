package com.nhn.academy.minidooray.gateway.domain.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//Todo : 입력 값을 불변 객체로 유지 해야 할 이유?
@Getter
@EqualsAndHashCode
@Setter
@ToString
public class AccountDto {
  String id;
  String email;
  String pwd;
}
