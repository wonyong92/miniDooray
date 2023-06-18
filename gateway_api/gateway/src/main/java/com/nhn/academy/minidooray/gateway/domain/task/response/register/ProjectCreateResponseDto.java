package com.nhn.academy.minidooray.gateway.domain.task.response.register;

import lombok.Data;

@Data
public class ProjectCreateResponseDto {

  private String adminId;
  private Integer projectId;
  private String name;

  private String status;
}
