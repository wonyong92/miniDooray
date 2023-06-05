package com.nhn.academy.minidooray.gateway.service.task.service;

import com.nhn.academy.minidooray.gateway.domain.task.response.read.ProjectList;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.ProjectRegister;
import com.nhn.academy.minidooray.gateway.domain.task.request.modify.ProjectStatusChange;
import com.nhn.academy.minidooray.gateway.service.task.resttemplate.RestTemplateTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
  final RestTemplateTaskService restTemplateTaskService;

  public ProjectList findMyProjets(String userName, Long size, Long page) {
    return restTemplateTaskService.findMyProjects(userName,size,page);
  }


  public void createProject(ProjectRegister registerRequest) {
    restTemplateTaskService.registerMyProject(registerRequest);
  }

  public void changeProjectStatus(ProjectStatusChange projectStatusChange){
    restTemplateTaskService.changeProjectStatus(projectStatusChange);
  }
}
