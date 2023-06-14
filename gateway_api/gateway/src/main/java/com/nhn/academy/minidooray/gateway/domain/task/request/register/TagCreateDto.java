package com.nhn.academy.minidooray.gateway.domain.task.request.register;

import lombok.Data;

@Data
public class TagCreateDto {

  private Integer projectId;
  private String name;
}
