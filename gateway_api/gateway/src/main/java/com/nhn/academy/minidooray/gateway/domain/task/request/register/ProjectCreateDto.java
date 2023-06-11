package com.nhn.academy.minidooray.gateway.domain.task.request.register;

import lombok.Data;


public class ProjectCreateDto {

  public ProjectCreateDto() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getAdminId() {
    return adminId;
  }

  String name;
  String adminId;

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }
}
