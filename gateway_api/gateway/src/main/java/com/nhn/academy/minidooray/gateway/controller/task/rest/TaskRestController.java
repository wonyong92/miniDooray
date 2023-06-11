package com.nhn.academy.minidooray.gateway.controller.task.rest;

import com.nhn.academy.minidooray.gateway.domain.task.request.modify.TaskUpdateRequestDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.TaskCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.response.register.TaskCreateResponseDto;
import com.nhn.academy.minidooray.gateway.service.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class TaskRestController {
  private final TaskService taskService;
  @GetMapping("/task")
  public ResponseEntity<String> readTask(
      @RequestParam Long taskId) {
    return ResponseEntity.ok(taskService.getTask(taskId));
  }

  @PostMapping("/task")
  public ResponseEntity<String> createTask(@RequestBody TaskCreateDto dto,@RequestParam Integer projectId){

    return ResponseEntity.created(null).body(taskService.registerTask(dto,projectId));
  }

  @PutMapping("/task")
  public ResponseEntity<Void> updateTask(@RequestBody TaskUpdateRequestDto dto,@RequestParam Integer taskId)
  {
    taskService.updateTask(dto,taskId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/task")
  public ResponseEntity<Void> deleteTask(@RequestParam Integer taskId) {
    taskService.deleteTask(taskId);
    return ResponseEntity.ok().build();
  }

//  @PostMapping
//  @ResponseStatus(HttpStatus.CREATED)
//  public TaskCreateResponseDto createTask(@RequestBody @Validated TaskCreateRequest taskCreateRequest, BindingResult bindingResult) {
//    if (bindingResult.hasErrors()) {
//      throw new ValidationFailedException(bindingResult);
//    }
//    return taskService.createTask(taskCreateRequest);
//  }
//
//  @PutMapping("/{taskId}")
//  public TaskUpdateResponseDto updateTask(@PathVariable(name = "taskId") Integer taskId,
//      @RequestBody @Validated TaskUpdateRequest taskUpdateRequest,
//      BindingResult bindingResult) {
//    if (bindingResult.hasErrors()) {
//      throw new ValidationFailedException(bindingResult);
//    }
//    return taskService.updateTask(taskUpdateRequest, taskId);
//  }
//
//  @DeleteMapping("/{taskId}")
//  public TaskDeleteResponseDto deleteTask(@PathVariable(name = "taskId") Integer taskId) {
//    return taskService.deleteTask(taskId);
//  }

}
