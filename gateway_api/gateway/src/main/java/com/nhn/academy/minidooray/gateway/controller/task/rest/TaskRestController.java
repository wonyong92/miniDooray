package com.nhn.academy.minidooray.gateway.controller.task.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.ProjectUpdateRequest;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.ProjectCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.response.register.ProjectCreateResponseDto;
import com.nhn.academy.minidooray.gateway.service.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TaskRestController {

  private final TaskService taskService;

  @PostMapping("/project")
  public ResponseEntity<ProjectCreateResponseDto> createProject(@RequestBody ProjectCreateDto dto) {
    dto.setAdminId(SecurityContextHolder.getContext().getAuthentication().getName());
    return ResponseEntity.created(null).body(taskService.createProject(dto));
  }

  @GetMapping("/project")
  public ResponseEntity<String> readProject(@RequestParam String projectId) throws JsonProcessingException {

    return ResponseEntity.ok(taskService.getProject(projectId));
  }

  @PutMapping("/project")
  public ResponseEntity<Void> test(@RequestParam String projectId, @RequestBody ProjectUpdateRequest dto) {
    taskService.updateProject(projectId, dto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/project")
  public ResponseEntity<Void> deleteProject(@RequestParam String projectId) {
    taskService.deleteProject(projectId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/project/names")
  public ResponseEntity<String> getMyProjects() {
    return ResponseEntity.ok(taskService.getProjects());
  }
}
