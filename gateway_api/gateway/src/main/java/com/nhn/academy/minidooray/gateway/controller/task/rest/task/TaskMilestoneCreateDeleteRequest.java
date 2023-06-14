package com.nhn.academy.minidooray.gateway.controller.task.rest.task;

import lombok.Data;

@Data
public class TaskMilestoneCreateDeleteRequest {

  private Integer taskId;

  private Integer milestoneId;

}
