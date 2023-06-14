package com.nhn.academy.minidooray.gateway.service.task.resttemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.properties.task.TaskApiServerProperties;
import com.nhn.academy.minidooray.gateway.controller.task.rest.task.TaskMilestoneCreateDeleteRequest;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.CommentUpdateRequestDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.ProjectUpdateRequest;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.TagUpdateRequestDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.TaskUpdateRequestDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.CommentCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.MilestoneCreateUpdateRequest;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.ProjectCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.TagCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.TaskCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.response.read.ProjectList;
import com.nhn.academy.minidooray.gateway.domain.task.response.register.ProjectCreateResponseDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component

public class RestTemplateTaskService {

  final RestTemplate restTemplate;
  final TaskApiServerProperties apiServerProperties;
  final ObjectMapper objectMapper;
  HttpHeaders headers;
  HttpEntity<String> httpEntity;


  public RestTemplateTaskService(RestTemplate restTemplate, TaskApiServerProperties apiServerProperties, ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.apiServerProperties = apiServerProperties;
    this.objectMapper = objectMapper;
    headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    httpEntity = new HttpEntity<>(headers);
  }

  public ProjectList findMyProjects(String userName, Long size, Long page) {
    return restTemplate.exchange(
        apiServerProperties.getFullUrl() + "/projects/{userNo}?size={size}&page={page}",
        HttpMethod.GET,
        httpEntity,
        ProjectList.class,
        userName, size, page
    ).getBody();
  }

  public ProjectCreateResponseDto registerMyProject(ProjectCreateDto registerRequest) {

    return restTemplate.postForEntity(
        apiServerProperties.getFullUrl() + apiServerProperties.getPostProject(),
        registerRequest,
        ProjectCreateResponseDto.class).getBody();
  }


  public String getProject(String projectId) {
    String result = restTemplate.getForObject(apiServerProperties.getFullUrl() + apiServerProperties.getGetProject(), String.class, projectId);
    log.debug("rest_template getProejct : {} ", result);
    return result;
  }

  public void updateProject(String projectId, ProjectUpdateRequest dto) {
    HttpEntity<ProjectUpdateRequest> entity = new HttpEntity<>(dto);
    restTemplate.put(apiServerProperties.getFullUrl() + apiServerProperties.getPutProject(), entity, projectId);
  }

  public void deleteProject(String projectId) {
    restTemplate.delete(apiServerProperties.getFullUrl() + apiServerProperties.getDelProject(), projectId);
  }

  public String getProjects(String clientId) {
    return restTemplate.getForObject(apiServerProperties.getFullUrl() + apiServerProperties.getGetProjects() + "/" + clientId, String.class);
  }

  public String getTask(Long taskId) {
    return restTemplate.getForObject(apiServerProperties.getFullUrl() + apiServerProperties.getGetTask(), String.class, taskId);
  }

  public String registerTask(TaskCreateDto dto, Integer projectId) {
    dto.setProjectId(projectId);
    dto.setWriterId(SecurityContextHolder.getContext().getAuthentication().getName());
    return restTemplate.postForObject(apiServerProperties.getFullUrl() + apiServerProperties.getPostTask(), dto, String.class);
  }

  public void updateTask(TaskUpdateRequestDto dto, Integer taskId) {
    restTemplate.put(apiServerProperties.getFullUrl() + apiServerProperties.getPutTask(), dto, taskId);
  }

  public void deleteTask(Integer taskId) {
    restTemplate.delete(apiServerProperties.getFullUrl() + apiServerProperties.getDelTask(), taskId);
  }

  public String readMilestone(Integer milestoneId) {
    return restTemplate.getForObject(apiServerProperties.getFullUrl() + apiServerProperties.getGetMilestone(), String.class, milestoneId);
  }

  public void updateMilestone(MilestoneCreateUpdateRequest dto, Integer milestoneId) {
    restTemplate.put(apiServerProperties.getFullUrl() + apiServerProperties.getPostMilestone(), dto, milestoneId);
  }

  public String registerMilestone(MilestoneCreateUpdateRequest dto) {
    return restTemplate.postForObject(apiServerProperties.getFullUrl() + apiServerProperties.getPostMilestone(), dto, String.class);
  }

  public void deleteMilestone(Integer milestoneId) {
    restTemplate.delete(apiServerProperties.getFullUrl() + apiServerProperties.getDelMilestone(), milestoneId);
  }

  public String getComment(Integer commentId) {
    return restTemplate.getForObject(apiServerProperties.getFullUrl() + apiServerProperties.getGetComment(), String.class, commentId);
  }

  public String registerComment(CommentCreateDto dto) {
//    dto.setWriterId(SecurityContextHolder.getContext().getAuthentication().getName());
    return restTemplate.postForObject(apiServerProperties.getFullUrl() + apiServerProperties.getPostComment(), dto, String.class);
  }

  public void updateComment(CommentUpdateRequestDto dto, String commentId) {
    restTemplate.put(apiServerProperties.getFullUrl() + apiServerProperties.getPutComment(), dto, commentId);

  }

  public void deleteComment(Integer commentId) {
    restTemplate.delete(apiServerProperties.getFullUrl() + apiServerProperties.getDelComment(), commentId);
  }

  public String getTag(Integer commentId) {
    return restTemplate.getForObject(apiServerProperties.getFullUrl() + apiServerProperties.getGetTag(), String.class, commentId);
  }

  public String registerTag(TagCreateDto dto) {
    return restTemplate.postForObject(apiServerProperties.getFullUrl() + apiServerProperties.getPostTag(), dto, String.class);
  }

  public void updateTag(TagUpdateRequestDto dto, Integer commentId) {
    restTemplate.put(apiServerProperties.getFullUrl() + apiServerProperties.getPutTag(), dto, commentId);

  }

  public void deleteTag(Integer commentId) {
    restTemplate.delete(apiServerProperties.getFullUrl() + apiServerProperties.getDelTag(), commentId);
  }

  public String createTaskMilestone(TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest) {
    return restTemplate.postForObject(apiServerProperties.getFullUrl() + apiServerProperties.getPostTaskMilestone(), taskMilestoneCreateDeleteRequest, String.class);
  }

  public void deleteTaskMilestone(TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest) throws JsonProcessingException {
    restTemplate.exchange(apiServerProperties.getFullUrl() + apiServerProperties.getDelTaskMilestone(), HttpMethod.DELETE, new HttpEntity<>(objectMapper.writeValueAsString(taskMilestoneCreateDeleteRequest)), Void.class);
  }


}
