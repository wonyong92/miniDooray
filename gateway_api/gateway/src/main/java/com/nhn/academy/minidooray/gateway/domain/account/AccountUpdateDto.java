package com.nhn.academy.minidooray.gateway.domain.account;

import lombok.Data;

@Data
public class AccountUpdateDto {

  String id;
  String email;
  String pwd;
}
