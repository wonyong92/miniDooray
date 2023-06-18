package com.nhn.academy.minidooray.gateway.controller.task.rest.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhn.academy.minidooray.gateway.service.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/taskMilestones")
public class TaskAndMilestoneRestController {

  private final TaskService taskService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<String> createTaskMilestone(@RequestBody TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest) {
    return ResponseEntity.ok(taskService.createTaskMilestone(taskMilestoneCreateDeleteRequest));
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteTaskMilestone(@RequestBody TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest) throws JsonProcessingException {
    taskService.deleteTaskMilestone(taskMilestoneCreateDeleteRequest);
    return ResponseEntity.noContent().build();
  }
}
