package com.nhn.academy.minidooray.gateway.service.task.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.ProjectUpdateRequest;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.TaskUpdateRequestDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.ProjectCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.TaskCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.response.read.ProjectList;
import com.nhn.academy.minidooray.gateway.domain.task.response.register.ProjectCreateResponseDto;
import com.nhn.academy.minidooray.gateway.service.task.resttemplate.RestTemplateTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  final RestTemplateTaskService restTemplateTaskService;
  final ObjectMapper objectMapper;

  public ProjectList findProject(String userName, Long size, Long page) {

    return restTemplateTaskService.findMyProjects(userName, size, page);
  }


  public ProjectCreateResponseDto createProject(ProjectCreateDto registerRequest) {

    return restTemplateTaskService.registerMyProject(registerRequest);
  }


  public String getProject(String projectId) throws JsonProcessingException {
    String result = restTemplateTaskService.getProject(projectId);
    JsonNode validating = objectMapper.readTree(result);
    if (validating.isEmpty() || validating.get("status").toString().contains("TERMINATION")) {
      return "";
    }
    return result;
  }

  public void updateProject(String projectId, ProjectUpdateRequest dto) {
    restTemplateTaskService.updateProject(projectId, dto);
  }

  public void deleteProject(String projectId) {
    restTemplateTaskService.deleteProject(projectId);
  }

  public String getProjects() {
    return restTemplateTaskService.getProjects(SecurityContextHolder.getContext().getAuthentication().getName());
  }

  public String getTask(Long taskId) {
    return restTemplateTaskService.getTask(taskId);
  }

  public String registerTask(TaskCreateDto dto, Integer projectId){
    return restTemplateTaskService.registerTask(dto,projectId);
  }

  public void updateTask(TaskUpdateRequestDto dto, Integer taskId) {
    restTemplateTaskService.updateTask(dto,taskId);
  }

  public void deleteTask(Integer taskId) {
    restTemplateTaskService.deleteTask(taskId);
  }
}
