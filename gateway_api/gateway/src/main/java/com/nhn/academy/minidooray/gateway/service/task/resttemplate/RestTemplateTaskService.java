package com.nhn.academy.minidooray.gateway.service.task.resttemplate;

import com.nhn.academy.minidooray.gateway.config.properties.task.TaskApiServerProperties;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.ProjectUpdateRequest;

import com.nhn.academy.minidooray.gateway.domain.task.request.register.ProjectCreateDto;

import com.nhn.academy.minidooray.gateway.domain.task.response.read.ProjectList;
import com.nhn.academy.minidooray.gateway.domain.task.response.register.ProjectCreateResponseDto;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Slf4j
@Component
public class RestTemplateTaskService {

  final RestTemplate restTemplate;
  final TaskApiServerProperties apiServerProperties;
  String serverUrl;
  HttpHeaders headers;
  HttpEntity<String> httpEntity;

  public RestTemplateTaskService(RestTemplate restTemplate, TaskApiServerProperties apiServerProperties) {
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
        "http://localhost:8081/projects",
        registerRequest,
        ProjectCreateResponseDto.class).getBody();
  }



  public String getProject(String projectId) {
    String result = restTemplate.getForObject(apiServerProperties.getFullUrl()+apiServerProperties.getGetProject(),String.class,projectId);
    log.debug("rest_template getProejct : {} ",result);
    return result;
  }

  public void updateProject(String projectId, ProjectUpdateRequest dto) {
    HttpEntity<ProjectUpdateRequest> entity = new HttpEntity<>(dto);
    restTemplate.put(apiServerProperties.getFullUrl()+apiServerProperties.getPutProject(),entity,projectId);
  }

  public void deleteProject(String projectId){
    restTemplate.delete(apiServerProperties.getFullUrl()+apiServerProperties.getDelProject(),projectId);
  }
}
