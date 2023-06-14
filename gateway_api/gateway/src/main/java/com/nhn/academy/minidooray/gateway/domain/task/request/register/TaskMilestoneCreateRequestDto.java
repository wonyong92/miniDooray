package com.nhn.academy.minidooray.gateway.domain.task.request.register;

import lombok.Data;

@Data
public class TaskMilestoneCreateRequestDto {

  private Integer taskId;

  private Integer milestoneId;

}
