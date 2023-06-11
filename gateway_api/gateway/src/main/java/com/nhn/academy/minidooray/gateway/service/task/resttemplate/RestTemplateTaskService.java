package com.nhn.academy.minidooray.gateway.service.task.resttemplate;

import com.nhn.academy.minidooray.gateway.config.properties.task.ProjectApiServerProperties;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.ProjectUpdateRequest;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.TaskUpdateRequestDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.ProjectCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.TaskCreateDto;
import com.nhn.academy.minidooray.gateway.domain.task.response.read.ProjectList;
import com.nhn.academy.minidooray.gateway.domain.task.response.register.ProjectCreateResponseDto;
import com.nhn.academy.minidooray.gateway.domain.task.response.register.TaskCreateResponseDto;
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
  final ProjectApiServerProperties apiServerProperties;
  String serverUrl;
  HttpHeaders headers;
  HttpEntity<String> httpEntity;

  public RestTemplateTaskService(RestTemplate restTemplate, ProjectApiServerProperties apiServerProperties) {
    this.restTemplate = restTemplate;
    this.apiServerProperties = apiServerProperties;
    serverUrl = apiServerProperties.getUrl() + ":" + apiServerProperties.getPort();
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
        apiServerProperties.getFullUrl()+apiServerProperties.getPostProject(),
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
    return restTemplate.getForObject(apiServerProperties.getFullUrl()+apiServerProperties.getGetTask(),String.class,taskId);
  }

  public String registerTask(TaskCreateDto dto, Integer projectId){
    dto.setProjectId(projectId);
    dto.setWriterId(SecurityContextHolder.getContext().getAuthentication().getName());
    return restTemplate.postForObject(apiServerProperties.getFullUrl()+apiServerProperties.getPostTask(),dto,String.class);
  }

  public void updateTask(TaskUpdateRequestDto dto, Integer taskId) {
    restTemplate.put(apiServerProperties.getFullUrl()+apiServerProperties.getPutTask(),dto,taskId);
  }

  public void deleteTask(Integer taskId) {
    restTemplate.delete(apiServerProperties.getFullUrl()+apiServerProperties.getDelTask(),taskId);
  }
}
