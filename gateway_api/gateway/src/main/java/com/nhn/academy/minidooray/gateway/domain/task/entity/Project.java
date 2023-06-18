package com.nhn.academy.minidooray.gateway.domain.task.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Project {

  private Long projectNo;

  private String name;

  private String status;

  private LocalDateTime createdAt;

}
