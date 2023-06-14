package com.nhn.academy.minidooray.gateway.domain.task.request.register;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MilestoneCreateUpdateRequest {

  private Integer projectId;

  private String name;

  private LocalDate startAt;

  private LocalDate endAt;

}
