package com.nhn.academy.minidooray.gateway.controller.task.view;

import com.nhn.academy.minidooray.gateway.domain.task.response.read.ProjectList;
import com.nhn.academy.minidooray.gateway.service.task.service.TaskService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TaskViewController {

  private final TaskService taskService;

  @GetMapping("/projects")
  public ResponseEntity<ProjectList> showMyProjects(HttpServletRequest request, Model model,
      @RequestParam(required = false, value = "size") Long size,
      @RequestParam(required = false, value = "page") Long page) {
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    ProjectList myProjectsPage = taskService.findProject(userName, size, page);

    return ResponseEntity.ok(myProjectsPage);
  }

  @GetMapping("/project/register")
  public String showRegisterProjectPage() {
    return "project/register";
  }

  @GetMapping("/{projectNo}/menu")
  public String showProjectMenu(@PathVariable String projectNo, Model model) {
    model.addAttribute("projectNo", projectNo);
    return "project/menu/menu";
  }


}
