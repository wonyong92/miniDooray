package com.nhn.academy.minidooray.gateway.domain.task.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter

public enum ProjectStatus {
  ACTIVE("a"),SLEEP("b"),END("c");

  public String getName(){
    return this.name();
  }
  String data;

  public String getData() {
    return data;
  }

  ProjectStatus(String data) {
    this.data = data;
  }
}
