package com.nhn.academy.minidooray.gateway.controller.task.rest.task;

import com.nhn.academy.minidooray.gateway.domain.task.request.modify.TaskUpdateRequestDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.TaskCreateDto;
import com.nhn.academy.minidooray.gateway.service.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class TaskRestController {

  private final TaskService taskService;

  @GetMapping("/task/{taskId}")
  public ResponseEntity<String> readTask(
      @PathVariable Long taskId) {
    return ResponseEntity.ok(taskService.getTask(taskId));
  }

  @PostMapping("/task/{taskId}")
  public ResponseEntity<String> createTask(@RequestBody TaskCreateDto dto, @PathVariable Integer projectId) {

    return ResponseEntity.created(null).body(taskService.registerTask(dto, projectId));
  }

  @PutMapping("/task/{taskId}")
  public ResponseEntity<Void> updateTask(@RequestBody TaskUpdateRequestDto dto, @PathVariable Integer taskId) {
    taskService.updateTask(dto, taskId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/task/{taskId}")
  public ResponseEntity<Void> deleteTask(@PathVariable Integer taskId) {
    taskService.deleteTask(taskId);
    return ResponseEntity.ok().build();
  }

}
