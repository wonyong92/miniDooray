package com.nhn.academy.minidooray.gateway.domain.task.request.modify;

import com.nhn.academy.minidooray.gateway.domain.task.entity.ProjectStatus;
import lombok.Data;

@Data
public class ProjectStatusChange {

  Long projectId;
  ProjectStatus status;

}
