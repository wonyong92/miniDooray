package com.nhn.academy.minidooray.gateway.controller.task.rest;

import com.nhn.academy.minidooray.gateway.domain.task.entity.ProjectStatus;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.ProjectRegister;
import com.nhn.academy.minidooray.gateway.service.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor

public class TaskRestController {

  private final TaskService taskService;


  @PostMapping("/project/register")
  public String registerProject(ProjectRegister registerRequest){
    taskService.createProject(registerRequest);
    return "redirect:/projects";
  }

  @GetMapping("/project/{projectNo}/status")
  public String showModifyProjectStatus(@PathVariable String projectNo, Model model){
    model.addAttribute("projectNo", projectNo);
    return "project/status";
  }

  @PostMapping("/project/enumtest")
  public String test(@RequestBody ProjectStatus projectStatus)
  {
    return projectStatus.name();
  }
}
