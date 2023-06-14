package com.nhn.academy.minidooray.gateway.controller.task.rest.milestone;

import com.nhn.academy.minidooray.gateway.domain.task.request.register.MilestoneCreateUpdateRequest;
import com.nhn.academy.minidooray.gateway.service.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/milestone")
public class MileStoneRestController {

  final TaskService taskService;


  @GetMapping("/{milestoneId}")
  public ResponseEntity<String> readMilestone(@PathVariable(name = "milestoneId") Integer milestoneId) {
    return ResponseEntity.ok(taskService.readMilestone(milestoneId));
  }

  @PutMapping("/{milestoneId}")
  public ResponseEntity<String> updateMilestone(@Validated @RequestBody MilestoneCreateUpdateRequest milestoneCreateUpdateRequest,
      @PathVariable(name = "milestoneId") Integer milestoneId) {
    taskService.updateMilestone(milestoneCreateUpdateRequest, milestoneId);
    return ResponseEntity.status(200).build();
  }

  @PostMapping
  public ResponseEntity<String> createMilestone(@Validated @RequestBody MilestoneCreateUpdateRequest milestoneCreateUpdateRequest) {

    return ResponseEntity.status(200).body(taskService.registerMilestone(milestoneCreateUpdateRequest));
  }

  @DeleteMapping("/{milestoneId}")
  public ResponseEntity<Void> deleteMilestone(@PathVariable(name = "milestoneId") Integer milestoneId) {
    taskService.deleteMilestone(milestoneId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
