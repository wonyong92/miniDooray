package com.nhnacademy.minidooray.account.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name="member")
@ToString
@Getter//없으면 null
public class Member {
  @Id
  String id;
  String email;
  String pwd;

}
