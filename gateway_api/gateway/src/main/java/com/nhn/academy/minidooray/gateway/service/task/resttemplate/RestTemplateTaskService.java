package com.nhn.academy.minidooray.gateway.service.task.resttemplate;

import com.nhn.academy.minidooray.gateway.config.properties.task.TaskApiServerProperties;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.ProjectStatusChange;
import com.nhn.academy.minidooray.gateway.domain.task.response.read.ProjectList;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.ProjectRegister;
import com.nhn.academy.minidooray.gateway.domain.task.response.register.ProjectRegisterResponse;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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
    serverUrl = apiServerProperties.getUrl()+":"+apiServerProperties.getPort();
    headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    httpEntity = new HttpEntity<>(headers);
  }

  public ProjectList findMyProjects(String userName,Long size, Long page){
    String serverUrl = apiServerProperties.getUrl()+":"+apiServerProperties.getPort();
    return restTemplate.exchange(
        serverUrl+"/projects/{userNo}?size={size}&page={page}",
        HttpMethod.GET,
        httpEntity,
        ProjectList.class,
        userName, size, page
    ).getBody();
  }

  public ProjectRegisterResponse registerMyProject(ProjectRegister registerRequest) {
    return restTemplate.postForEntity(
            "http://localhost:8081/projects",
            registerRequest,
            ProjectRegisterResponse.class).getBody();
  }


  public void changeProjectStatus(ProjectStatusChange projectStatusChange) {
  }
}
