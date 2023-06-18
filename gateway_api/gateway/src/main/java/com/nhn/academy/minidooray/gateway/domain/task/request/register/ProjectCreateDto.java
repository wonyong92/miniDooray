package com.nhn.academy.minidooray.gateway.domain.task.request.register;

public class ProjectCreateDto {

  String name;
  String adminId;

  public ProjectCreateDto() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAdminId() {
    return adminId;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }
}
