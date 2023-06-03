package com.nhnacademy.minidooray.account.command;

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

}
